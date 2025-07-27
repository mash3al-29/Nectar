package com.mashaal.ecommerce_app.domain.repository

import com.mashaal.ecommerce_app.domain.model.Cart
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCart(): Flow<Cart>
    suspend fun addToCart(productId: Int, quantity: Int, portion: String)
    suspend fun removeFromCart(productId: Int)

    suspend fun removeAllFromCart()

    suspend fun updateQuantity(productId: Int, quantity: Int)
    suspend fun getCartItemCount(): Int
    suspend fun getTotalQuantity(): Int
    suspend fun isProductInCart(productId: Int): Boolean
} 