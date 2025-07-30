package com.mashaal.ecommerce_app.ui.ProductScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.mashaal.ecommerce_app.domain.usecase.GetProductByIdUseCase
import com.mashaal.ecommerce_app.domain.usecase.AddToCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.IsProductInCartUseCase
import javax.inject.Inject

@HiltViewModel
class ProductScreenViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val isProductInCartUseCase: IsProductInCartUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<ProductScreenState>(ProductScreenState.Loading)
    val state: StateFlow<ProductScreenState> = _state

    fun onEvent(event: ProductScreenEvent) {
        when (event) {
            is ProductScreenEvent.OnQuantityChanged -> {
                updateSuccessState { it.copy(quantity = event.quantity) }
            }
            is ProductScreenEvent.OnFavoriteToggled -> {
                updateSuccessState { it.copy(isFavorite = !it.isFavorite) }
            }
            is ProductScreenEvent.OnAddToCartClicked -> {
                addToCart()
            }
            is ProductScreenEvent.OnShareClicked -> {
                shareProduct()
            }
            is ProductScreenEvent.OnShareConsumed -> {
                updateSuccessState { it.copy(shareData = null) }
            }
        }
    }
    
    fun loadProductById(productId: Int) {
        viewModelScope.launch {
            _state.value = ProductScreenState.Loading
            try {
                val product = getProductByIdUseCase(productId)
                if (product != null) {
                    val isInCart = isProductInCartUseCase(productId)
                    _state.value = ProductScreenState.Success(
                        product = product,
                        isInCart = isInCart
                    )
                } else {
                    _state.value = ProductScreenState.Error("Product not found")
                }
            } catch (e: Exception) {
                _state.value = ProductScreenState.Error(e.message ?: "Failed to load product")
            }
        }
    }
    
    private fun updateSuccessState(update: (ProductScreenState.Success) -> ProductScreenState.Success) {
        val currentState = _state.value
        if (currentState is ProductScreenState.Success) {
            _state.value = update(currentState)
        }
    }
    
    private fun addToCart() {
        val currentState = _state.value
        if (currentState is ProductScreenState.Success && currentState.product != null) {
            viewModelScope.launch {
                try {
                    addToCartUseCase(
                        productId = currentState.product.id,
                        quantity = currentState.quantity,
                        portion = currentState.product.detail
                    )
                    updateSuccessState { it.copy(addToCartSuccess = true, isInCart = true) }
                } catch (e: Exception) {
                    println("Failed to add product to cart: ${e.message}")
                }
            }
        }
    }
    
    private fun shareProduct() {
        val currentState = _state.value
        if (currentState is ProductScreenState.Success && currentState.product != null) {
            val product = currentState.product
            val shareData = ShareData(
                title = "Share ${product.name}",
                text = "Check out this product: ${product.name} - ${product.description} Only $${product.price}!"
            )
            updateSuccessState { it.copy(shareData = shareData) }
        }
    }
}
