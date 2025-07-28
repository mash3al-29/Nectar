package com.mashaal.ecommerce_app.ui.ProductScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.mashaal.ecommerce_app.domain.usecase.GetProductByIdUseCase
import com.mashaal.ecommerce_app.domain.usecase.AddToCartUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class ProductScreenViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ProductScreenState())
    val state: StateFlow<ProductScreenState> = _state.asStateFlow()

    fun onEvent(event: ProductScreenEvent) {
        when (event) {
            is ProductScreenEvent.OnQuantityChanged -> {
                _state.update { it.copy(quantity = event.quantity) }
            }
            is ProductScreenEvent.OnFavoriteToggled -> {
                _state.update { it.copy(isFavorite = !it.isFavorite) }
            }
            is ProductScreenEvent.OnAddToCartClicked -> {
                addToCart()
            }
            is ProductScreenEvent.OnShareClicked -> {
                shareProduct()
            }
            is ProductScreenEvent.OnShareConsumed -> {
                _state.update { it.copy(shareData = null) }
            }
        }
    }
    
    fun loadProductById(productId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val product = getProductByIdUseCase.execute(productId)
                if (product != null) {
                    _state.update { it.copy(
                        product = product,
                        isLoading = false
                    ) }
                } else {
                    _state.update { it.copy(
                        isLoading = false,
                        error = "Product not found"
                    ) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                ) }
            }
        }
    }
    
    private fun addToCart() {
        val currentState = _state.value
        val product = currentState.product
        if (product != null) {
            viewModelScope.launch {
                try {
                    addToCartUseCase.execute(
                        productId = product.id,
                        quantity = currentState.quantity,
                        portion = product.detail
                    )
                    _state.update { it.copy(
                        addToCartSuccess = true
                    ) }
                    delay(2000)
                    _state.update { it.copy(addToCartSuccess = false) }
                } catch (e: Exception) {
                    _state.update { it.copy(
                        error = e.message ?: "Failed to add to cart"
                    ) }
                }
            }
        }
    }

    private fun shareProduct() {
        val currentState = _state.value
        val product = currentState.product
        if (product != null) {
            val shareData = ShareData(
                title = "Share Product",
                text = "Check out this product: ${product.name} for $${product.price}"
            )
            _state.update { it.copy(shareData = shareData) }
        }
    }
}
