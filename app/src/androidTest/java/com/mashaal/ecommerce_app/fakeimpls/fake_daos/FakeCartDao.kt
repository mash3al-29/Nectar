package com.mashaal.ecommerce_app.fakeimpls.fake_daos

import com.mashaal.ecommerce_app.data.Dao.CartDao
import com.mashaal.ecommerce_app.data.Entity.CartEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeCartDaoUI : CartDao {
    private val cartItems = mutableListOf<CartEntity>()
    private val cartFlow = MutableStateFlow<List<CartEntity>>(emptyList())

    override fun getAllCartItems(): Flow<List<CartEntity>> {
        return cartFlow.asStateFlow()
    }

    override suspend fun getCartItemByProductId(productId: Int): CartEntity? {
        return cartItems.find { it.productId == productId }
    }

    override suspend fun insertCartItem(cartItem: CartEntity) {
        cartItems.removeAll { it.productId == cartItem.productId }
        cartItems.add(cartItem)
        emitCart()
    }

    override suspend fun updateCartItem(cartItem: CartEntity) {
        insertCartItem(cartItem) // same behavior
    }

    override suspend fun updateQuantity(productId: Int, quantity: Int) {
        val existing = cartItems.find { it.productId == productId }
        if (existing != null) {
            val updated = existing.copy(quantity = quantity)
            insertCartItem(updated)
        }
    }

    override suspend fun deleteCartItem(productId: Int) {
        cartItems.removeAll { it.productId == productId }
        emitCart()
    }

    override suspend fun removeAllFromCart() {
        cartItems.clear()
        emitCart()
    }

    override suspend fun getCartItemCount(): Int {
        return cartItems.size
    }

    override suspend fun getTotalQuantity(): Int {
        return cartItems.sumOf { it.quantity }
    }

    private fun emitCart() {
        cartFlow.value = cartItems.toList()
    }
} 