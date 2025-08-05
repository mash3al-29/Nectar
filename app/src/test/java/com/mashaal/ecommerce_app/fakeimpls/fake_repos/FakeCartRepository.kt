package com.mashaal.ecommerce_app.fake

import com.mashaal.ecommerce_app.data.Entity.CartEntity
import com.mashaal.ecommerce_app.domain.model.Cart
import com.mashaal.ecommerce_app.domain.model.CartItem
import com.mashaal.ecommerce_app.domain.model.toDomain
import com.mashaal.ecommerce_app.domain.repository.CartRepository
import com.mashaal.ecommerce_app.fakeimpls.fake_daos.FakeCartDao
import com.mashaal.ecommerce_app.fakeimpls.fake_daos.FakeProductDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*

class FakeCartRepository(
    private val cartDao: FakeCartDao,
    private val productDao: FakeProductDao
) : CartRepository {

    override fun getCart(): Flow<Cart> {
        return cartDao.getAllCartItems().map { entities ->
            val items = entities.mapNotNull { entity ->
                val product = productDao.getProductById(entity.productId)?.toDomain()
                product?.let {
                    CartItem(it, entity.quantity, entity.addedAt)
                }
            }
            Cart(items).calculateTotals()
        }
    }

    override suspend fun addToCart(productId: Int, quantity: Int) {
        val existing = cartDao.getCartItemByProductId(productId)
        val updatedQty = (existing?.quantity ?: 0) + quantity
        val newEntity = CartEntity(
            productId = productId,
            quantity = updatedQty,
            addedAt = 545654
        )
        cartDao.insertCartItem(newEntity)
    }

    override suspend fun removeFromCart(productId: Int) {
        cartDao.deleteCartItem(productId)
    }

    override suspend fun removeAllFromCart() {
        cartDao.removeAllFromCart()
    }

    override suspend fun updateQuantity(productId: Int, quantity: Int) {
        cartDao.updateQuantity(productId, quantity)
    }

    override suspend fun getCartItemCount(): Int {
        return cartDao.getCartItemCount()
    }

    override suspend fun getTotalQuantity(): Int {
        return cartDao.getTotalQuantity()
    }

    override suspend fun isProductInCart(productId: Int): Boolean {
        return cartDao.getCartItemByProductId(productId) != null
    }
}
