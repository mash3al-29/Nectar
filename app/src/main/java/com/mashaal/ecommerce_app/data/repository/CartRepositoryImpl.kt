package com.mashaal.ecommerce_app.data.repository

import com.mashaal.ecommerce_app.data.Dao.CartDao
import com.mashaal.ecommerce_app.data.Dao.ProductDao
import com.mashaal.ecommerce_app.data.Entity.CartEntity
import com.mashaal.ecommerce_app.domain.model.Cart
import com.mashaal.ecommerce_app.domain.model.CartItem
import com.mashaal.ecommerce_app.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    private val productDao: ProductDao
) : CartRepository {
    
    override fun getCart(): Flow<Cart> {
        return cartDao.getAllCartItems().map { cartEntities ->
            val cartItems = cartEntities.mapNotNull { cartEntity ->
                val product = productDao.getProductById(cartEntity.productId)
                product?.let {
                    CartItem(
                        product = it.toDomainModel(),
                        quantity = cartEntity.quantity,
                        portion = cartEntity.portion,
                        addedAt = cartEntity.addedAt
                    )
                }
            }
            Cart(items = cartItems).calculateTotals()
        }
    }
    
    override suspend fun addToCart(productId: Int, quantity: Int, portion: String) {
        val existingItem = cartDao.getCartItemByProductId(productId)
        if (existingItem != null) {
            val newQuantity = existingItem.quantity + quantity
            cartDao.updateQuantity(productId, newQuantity)
        } else {
            val cartEntity = CartEntity(
                productId = productId,
                quantity = quantity,
                portion = portion
            )
            cartDao.insertCartItem(cartEntity)
        }
    }
    
    override suspend fun removeFromCart(productId: Int) {
        cartDao.deleteCartItem(productId)
    }
    
    override suspend fun updateQuantity(productId: Int, quantity: Int) {
        if (quantity <= 0) {
            cartDao.deleteCartItem(productId)
        } else {
            cartDao.updateQuantity(productId, quantity)
        }
    }
    
    override suspend fun getCartItemCount(): Int {
        return cartDao.getCartItemCount()
    }
    
    override suspend fun getTotalQuantity(): Int {
        return cartDao.getTotalQuantity() ?: 0
    }
    
    override suspend fun isProductInCart(productId: Int): Boolean {
        return cartDao.getCartItemByProductId(productId) != null
    }
} 