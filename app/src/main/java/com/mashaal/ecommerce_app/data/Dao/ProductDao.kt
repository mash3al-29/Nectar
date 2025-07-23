package com.mashaal.ecommerce_app.data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mashaal.ecommerce_app.data.Entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)
    
    @Query("SELECT DISTINCT category FROM products")
    suspend fun getAllCategories(): List<String>
    
    @Query("SELECT * FROM products WHERE category = :category")
    suspend fun getProductsByCategory(category: String): List<ProductEntity>
    
    @Query("SELECT * FROM products WHERE category = :category AND price >= :minPrice AND price <= :maxPrice")
    suspend fun getProductsByCategoryAndPrice(category: String, minPrice: Double, maxPrice: Double): List<ProductEntity>
    
    @Query("SELECT * FROM products WHERE category = :category AND detail LIKE '%' || :detail || '%'")
    suspend fun getProductsByCategoryAndDetail(category: String, detail: String): List<ProductEntity>
    
    @Query("SELECT * FROM products WHERE category = :category AND price >= :minPrice AND price <= :maxPrice AND detail LIKE '%' || :detail || '%'")
    suspend fun getProductsByCategoryPriceAndDetail(category: String, minPrice: Double, maxPrice: Double, detail: String): List<ProductEntity>
}