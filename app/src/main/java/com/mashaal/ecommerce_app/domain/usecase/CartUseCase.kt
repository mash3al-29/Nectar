package com.mashaal.ecommerce_app.domain.usecase

import com.mashaal.ecommerce_app.domain.model.Cart
import com.mashaal.ecommerce_app.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    fun execute(): Flow<Cart> {
        return cartRepository.getCart()
    }
}

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend fun execute(productId: Int, quantity: Int, portion: String) {
        cartRepository.addToCart(productId, quantity, portion)
    }
}

class RemoveFromCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend fun execute(productId: Int) {
        cartRepository.removeFromCart(productId)
    }
}

class UpdateCartQuantityUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend fun execute(productId: Int, quantity: Int) {
        cartRepository.updateQuantity(productId, quantity)
    }
}

class GetCartItemCountUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend fun execute(): Int {
        return cartRepository.getCartItemCount()
    }
}

class GetCartTotalQuantityUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend fun execute(): Int {
        return cartRepository.getTotalQuantity()
    }
}