package com.mashaal.ecommerce_app.ui.ProductScreen

import com.mashaal.ecommerce_app.domain.model.Product

data class ProductScreenState(
    val product: Product? = null,
    val quantity: Int = 1,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = true,
    val error: String? = null
){
    val totalPrice: Float
        get() = ((product?.price ?: 0.0) * quantity).toFloat()
}

sealed class ProductScreenEvent {
    data class OnQuantityChanged(val quantity: Int) : ProductScreenEvent()
    data object OnFavoriteToggled : ProductScreenEvent()
    data object OnAddToCartClicked : ProductScreenEvent()
    data object OnBackClicked : ProductScreenEvent()
}