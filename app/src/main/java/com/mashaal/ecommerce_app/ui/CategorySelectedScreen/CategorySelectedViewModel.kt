package com.mashaal.ecommerce_app.ui.CategorySelectedScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategorySelectedViewModel @Inject constructor(
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
    private val getProductsByCategoryAndPriceUseCase: GetProductsByCategoryAndPriceUseCase,
    private val getProductsByCategoryAndDetailUseCase: GetProductsByCategoryAndDetailUseCase,
    private val getProductsByCategoryPriceAndDetailUseCase: GetProductsByCategoryPriceAndDetailUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CategorySelectedState())
    val state: StateFlow<CategorySelectedState> = _state.asStateFlow()

    fun onEvent(event: CategorySelectedEvent) {
        when (event) {
            is CategorySelectedEvent.OnAddToCartClicked -> {
                addToCart(event.product)
            }
        }
    }

    private fun addToCart(product: Product) {
        viewModelScope.launch {
            try {
                addToCartUseCase.execute(
                    productId = product.id,
                    quantity = 1,
                    portion = product.detail
                )
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message ?: "Failed to add to cart") }
            }
        }
    }

    fun loadProductsByCategory(categoryName: String) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, categoryName = categoryName) }
                val products = getProductsByCategoryUseCase.execute(categoryName)
                _state.update { it.copy(
                    products = products,
                    filteredProducts = emptyList(),
                    isLoading = false
                ) }
                
                checkAndApplyFilters()
            } catch (e: Exception) {
                _state.update { it.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load products for $categoryName"
                ) }
            }
        }
    }

    fun applyFilterResults(priceRange: String?, productPortions: Set<String>) {
        if (priceRange != null || productPortions.isNotEmpty()) {
            _state.update { it.copy(
                selectedPriceRange = priceRange,
                selectedProductPortions = productPortions
            ) }
            
            if (_state.value.products.isNotEmpty()) {
                viewModelScope.launch {
                    applyFilters()
                }
            }
        }
    }

    private fun checkAndApplyFilters() {
        if (_state.value.selectedPriceRange != null || _state.value.selectedProductPortions.isNotEmpty()) {
            viewModelScope.launch {
                applyFilters()
            }
        }
    }

    private fun applyFilters() {
        viewModelScope.launch {
            try {
                val categoryName = _state.value.categoryName
                val priceRange = _state.value.selectedPriceRange
                val selectedProductPortions = _state.value.selectedProductPortions
                
                if (priceRange == null && selectedProductPortions.isEmpty()) {
                    _state.update { it.copy(
                        filteredProducts = emptyList(),
                        error = null
                    ) }
                    return@launch
                }
                
                val filteredProducts = fetchFilteredProducts(
                    categoryName, 
                    priceRange, 
                    selectedProductPortions
                )
                
                _state.update { it.copy(
                    filteredProducts = filteredProducts,
                    error = if (filteredProducts.isEmpty()) "No products match your filter criteria" else null
                ) }
            } catch (e: Exception) {
                _state.update { it.copy(
                    error = e.message ?: "Failed to apply filters"
                ) }
            }
        }
    }

    private suspend fun fetchFilteredProducts(
        categoryName: String,
        priceRange: String?,
        selectedProductPortions: Set<String>
    ): List<Product> {
        return when {
            priceRange != null && selectedProductPortions.isNotEmpty() -> {
                val results = mutableListOf<Product>()
                val (minPrice, maxPrice) = parsePriceRange(priceRange)
                
                for (portion in selectedProductPortions) {
                    val products = getProductsByCategoryPriceAndDetailUseCase.execute(
                        categoryName, minPrice, maxPrice, portion
                    )
                    results.addAll(products)
                }
                results.distinctBy { it.id }
            }
            priceRange != null -> {
                val (minPrice, maxPrice) = parsePriceRange(priceRange)
                getProductsByCategoryAndPriceUseCase.execute(categoryName, minPrice, maxPrice)
            }
            else -> {
                val results = mutableListOf<Product>()
                for (portion in selectedProductPortions) {
                    val products = getProductsByCategoryAndDetailUseCase.execute(categoryName, portion)
                    results.addAll(products)
                }
                results.distinctBy { it.id }
            }
        }
    }

    private fun parsePriceRange(priceRange: String): Pair<Double, Double> {
        return when (priceRange) {
            "Under $2" -> 0.0 to 2.0
            "$2 - $4" -> 2.0 to 4.0
            "$4 - $6" -> 4.0 to 6.0
            "$6 - $8" -> 6.0 to 8.0
            "$8 - $10" -> 8.0 to 10.0
            "$10 - $12" -> 10.0 to 12.0
            "$12 - $15" -> 12.0 to 15.0
            "$15 - $20" -> 15.0 to 20.0
            else -> 0.0 to Double.MAX_VALUE
        }
    }
}