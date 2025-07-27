package com.mashaal.ecommerce_app.data.Dao

import androidx.room.*
import com.mashaal.ecommerce_app.data.Entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart ORDER BY addedAt DESC")
    fun getAllCartItems(): Flow<List<CartEntity>>
    
    @Query("SELECT * FROM cart WHERE productId = :productId")
    suspend fun getCartItemByProductId(productId: Int): CartEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartEntity)
    
    @Update
    suspend fun updateCartItem(cartItem: CartEntity)
    
    @Query("UPDATE cart SET quantity = :quantity WHERE productId = :productId")
    suspend fun updateQuantity(productId: Int, quantity: Int)
    
    @Query("DELETE FROM cart WHERE productId = :productId")
    suspend fun deleteCartItem(productId: Int)

    @Query("DELETE FROM cart")
    suspend fun removeAllFromCart()
    
    @Query("SELECT COUNT(*) FROM cart")
    suspend fun getCartItemCount(): Int
    
    @Query("SELECT SUM(quantity) FROM cart")
    suspend fun getTotalQuantity(): Int?
} 