package com.mashaal.ecommerce_app.ui.ProductScreen

import com.mashaal.ecommerce_app.domain.model.Product

sealed class ProductScreenState {
    data object Loading : ProductScreenState()
    data class Success(
        val product: Product? = null,
        val quantity: Int = 1,
        val isFavorite: Boolean = false,
        val isInCart: Boolean = false,
        val addToCartSuccess: Boolean = false,
        val shareData: ShareData? = null
    ) : ProductScreenState() {
        val totalPrice: Float
            get() = ((product?.price ?: 0.0) * quantity).toFloat()
    }
    
    data class Error(val message: String) : ProductScreenState()
}

data class ShareData(
    val title: String,
    val text: String
)

sealed class ProductScreenEvent {
    data class OnQuantityChanged(val quantity: Int) : ProductScreenEvent()
    data object OnFavoriteToggled : ProductScreenEvent()
    data object OnAddToCartClicked : ProductScreenEvent()
    data object OnShareClicked : ProductScreenEvent()
    data object OnShareConsumed : ProductScreenEvent()
}