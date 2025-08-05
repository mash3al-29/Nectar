package com.mashaal.ecommerce_app.ui.CategorySelectedScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashaal.ecommerce_app.domain.model.Cart
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.domain.usecase.AddToCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.GetCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.GetProductsByCategoryAndDetailUseCase
import com.mashaal.ecommerce_app.domain.usecase.GetProductsByCategoryAndPriceUseCase
import com.mashaal.ecommerce_app.domain.usecase.GetProductsByCategoryPriceAndDetailUseCase
import com.mashaal.ecommerce_app.domain.usecase.GetProductsByCategoryUseCase
import com.mashaal.ecommerce_app.ui.FilterScreen.PriceRange
import com.mashaal.ecommerce_app.ui.FilterScreen.ProductPortion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class CategorySelectedViewModel @Inject constructor(
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
    private val getProductsByCategoryAndPriceUseCase: GetProductsByCategoryAndPriceUseCase,
    private val getProductsByCategoryAndDetailUseCase: GetProductsByCategoryAndDetailUseCase,
    private val getProductsByCategoryPriceAndDetailUseCase: GetProductsByCategoryPriceAndDetailUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    getCartUseCase: GetCartUseCase
) : ViewModel() {
    
    private val _categoryName = MutableStateFlow("")
    private val _selectedPriceRange = MutableStateFlow<PriceRange?>(null)
    private val _selectedProductPortions = MutableStateFlow<Set<ProductPortion>>(emptySet())

    val cartProductIds: StateFlow<Set<Int>> = getCartUseCase()
        .catch { emit(Cart(emptyList())) }
        .map { cart -> cart.items.map { it.product.id }.toSet() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    private val productsFlow = combine(
        _categoryName,
        _selectedPriceRange,
        _selectedProductPortions
    ) { categoryName, priceRange, productPortions ->
        Triple(categoryName, priceRange, productPortions)
    }.flatMapLatest { (categoryName, priceRange, productPortions) ->
        if (categoryName.isBlank()) {
            flowOf(emptyList())
        } else {
            when {
                priceRange != null && productPortions.isNotEmpty() -> {
                    val (minPrice, maxPrice) = parsePriceRange(priceRange)
                    combineProductDetailFlows(productPortions) { detail ->
                        getProductsByCategoryPriceAndDetailUseCase(categoryName, minPrice, maxPrice, detail)
                    }
                }
                priceRange != null -> {
                    val (minPrice, maxPrice) = parsePriceRange(priceRange)
                    getProductsByCategoryAndPriceUseCase(categoryName, minPrice, maxPrice)
                        .catch { emit(emptyList()) }
                }
                productPortions.isNotEmpty() -> {
                    combineProductDetailFlows(productPortions) { detail ->
                        getProductsByCategoryAndDetailUseCase(categoryName, detail)
                    }
                }
                else -> {
                    getProductsByCategoryUseCase(categoryName)
                        .catch { emit(emptyList()) }
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val state: StateFlow<CategorySelectedState> = combine(
        _categoryName,
        productsFlow,
        _selectedPriceRange,
        _selectedProductPortions
    ) { categoryName, products, priceRange, productPortions ->
        if (categoryName.isBlank()) {
            CategorySelectedState.Loading
        } else {
            CategorySelectedState.Success(
                products = if (priceRange == null && productPortions.isEmpty()) products else emptyList(),
                filteredProducts = if (priceRange != null || productPortions.isNotEmpty()) products else emptyList(),
                categoryName = categoryName,
                selectedPriceRange = priceRange,
                selectedProductPortions = productPortions
            )
        }
    }.catch { throwable ->
        emit(CategorySelectedState.Error(throwable.message ?: "Failed to load products"))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CategorySelectedState.Loading
    )

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
                addToCartUseCase(
                    productId = product.id,
                    quantity = 1
                )
            } catch (e: Exception) {
                println("Failed to add product to cart: ${e.message}")
            }
        }
    }

    fun loadProductsByCategory(categoryName: String) {
        _categoryName.value = categoryName
    }

    fun applyFilterResults(priceRange: PriceRange?, productPortions: Set<ProductPortion>) {
        _selectedPriceRange.value = priceRange
        _selectedProductPortions.value = productPortions
    }

    private fun combineProductDetailFlows(
        productPortions: Set<ProductPortion>,
        flowProvider: (String) -> Flow<List<Product>>
    ): Flow<List<Product>> {
        val detailFlows = productPortions.map { portion ->
            // Convert ProductPortion enum to text for database query - this is the only place we need text conversion
            val detailText = portion.stringResId.let { 
                // We'll need context here, but for now let's use a mapping approach
                when (portion) {
                    ProductPortion.KG_1 -> "1kg"
                    ProductPortion.L_1 -> "1L"
                    ProductPortion.G_500 -> "500g"
                    ProductPortion.G_300 -> "300g"
                    ProductPortion.G_400 -> "400g"
                    ProductPortion.PC_1 -> "1pc"
                    ProductPortion.G_150 -> "150g"
                    ProductPortion.PCS_12 -> "12pcs"
                    ProductPortion.G_250 -> "250g"
                    ProductPortion.BAGS_25 -> "25 bags"
                    ProductPortion.ML_500 -> "500ml"
                }
            }
            flowProvider(detailText).catch { emit(emptyList()) }
        }
        return if (detailFlows.isNotEmpty()) {
            combine(detailFlows) { flowResults: Array<List<Product>> ->
                flowResults.toList().flatten().distinctBy { it.id }
            }
        } else {
            flowOf(emptyList())
        }
    }

    private fun parsePriceRange(priceRange: PriceRange): Pair<Double, Double> {
        return priceRange.minPrice to priceRange.maxPrice
    }
}