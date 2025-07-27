package com.mashaal.ecommerce_app.domain.model

data class Cart(
    val items: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0,
    val totalItems: Int = 0
) {
    fun calculateTotals(): Cart {
        val calculatedTotalPrice = items.sumOf { it.totalPrice }
        val calculatedTotalItems = items.sumOf { it.quantity }
        return this.copy(
            totalPrice = calculatedTotalPrice,
            totalItems = calculatedTotalItems
        )
    }
}

data class CartItem(
    val product: Product,
    val quantity: Int,
    val portion: String,
    val addedAt: Long = System.currentTimeMillis()
) {
    val totalPrice: Double
        get() = product.price * quantity
} 