package com.mashaal.ecommerce_app.ui.MyCartScreen

import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.domain.model.CartItem as DomainCartItem

data class MyCartScreenState(
    val cartItems: List<CartItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val totalPrice: Double = 0.0
)

data class CartItem(
    val product: Product,
    val quantity: Int,
    val portion: String
) {
    val totalPrice: Double
        get() = product.price * quantity
}

fun DomainCartItem.toUIModel(): CartItem {
    return CartItem(
        product = this.product,
        quantity = this.quantity,
        portion = this.portion
    )
}

sealed class MyCartScreenEvent {
    data class OnQuantityChanged(val productId: Int, val quantity: Int) : MyCartScreenEvent()
    data class OnRemoveItem(val productId: Int) : MyCartScreenEvent()
    data object OnCheckoutClicked : MyCartScreenEvent()
} 