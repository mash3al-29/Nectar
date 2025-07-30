package com.mashaal.ecommerce_app.domain.usecase

import com.mashaal.ecommerce_app.domain.model.Cart
import com.mashaal.ecommerce_app.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(): Flow<Cart> {
        return cartRepository.getCart()
    }
}

class AddToCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: Int, quantity: Int, portion: String) {
        cartRepository.addToCart(productId, quantity, portion)
    }
}

class RemoveFromCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: Int) {
        cartRepository.removeFromCart(productId)
    }
}

class UpdateCartQuantityUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: Int, quantity: Int) {
        cartRepository.updateQuantity(productId, quantity)
    }
}

class RemoveAllFromCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke() {
        cartRepository.removeAllFromCart()
    }
}

class IsProductInCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(productId: Int): Boolean {
        return cartRepository.isProductInCart(productId)
    }
}