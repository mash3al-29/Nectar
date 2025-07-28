package com.mashaal.ecommerce_app.ui.SeeAllScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashaal.ecommerce_app.domain.extensions.groceries
import com.mashaal.ecommerce_app.domain.extensions.isBestSelling
import com.mashaal.ecommerce_app.domain.extensions.isExclusiveOffer
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.domain.usecase.AddToCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.GetAllProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SeeAllState())
    val state: StateFlow<SeeAllState> = _state.asStateFlow()

    fun onEvent(event: SeeAllEvent) {
        when (event) {
            is SeeAllEvent.OnAddToCartClicked -> {
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

    fun loadProducts(sectionType: SeeAllSectionType) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, sectionType = sectionType) }
                
                getAllProductsUseCase.execute()
                    .catch { e ->
                        _state.update { it.copy(
                            isLoading = false,
                            error = e.message ?: "Failed to load products"
                        )}
                    }
                    .collect { productsList ->
                        val filteredProducts = when (sectionType) {
                            SeeAllSectionType.EXCLUSIVE_OFFERS -> productsList.filter { it.isExclusiveOffer }
                            SeeAllSectionType.BEST_SELLING -> productsList.filter { it.isBestSelling }
                            SeeAllSectionType.GROCERIES -> productsList.filter { it.groceries }
                        }
                        
                        _state.update { it.copy(
                            products = filteredProducts,
                            isLoading = false
                        )}
                    }
            } catch (e: Exception) {
                _state.update { it.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load products"
                )}
            }
        }
    }
}