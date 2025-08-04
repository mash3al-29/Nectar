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

    @Query("SELECT * FROM products WHERE LOWER(category) = LOWER(:category)")
    fun getProductsByCategory(category: String): Flow<List<ProductEntity>>

    @Query("""
    SELECT * FROM products 
    WHERE LOWER(category) = LOWER(:category) 
    AND price BETWEEN :minPrice AND :maxPrice
""")
    fun getProductsByCategoryAndPrice(category: String, minPrice: Double, maxPrice: Double): Flow<List<ProductEntity>>

    @Query("""
    SELECT * FROM products 
    WHERE LOWER(category) = LOWER(:category) 
    AND LOWER(detail) LIKE '%' || LOWER(:detail) || '%'
""")
    fun getProductsByCategoryAndDetail(category: String, detail: String): Flow<List<ProductEntity>>

    @Query("""
    SELECT * FROM products 
    WHERE LOWER(category) = LOWER(:category) 
    AND price BETWEEN :minPrice AND :maxPrice 
    AND LOWER(detail) LIKE '%' || LOWER(:detail) || '%'
""")
    fun getProductsByCategoryPriceAndDetail(
        category: String,
        minPrice: Double,
        maxPrice: Double,
        detail: String
    ): Flow<List<ProductEntity>>

    @Query("""
    SELECT * FROM products 
    WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' 
    OR LOWER(description) LIKE '%' || LOWER(:query) || '%' 
    OR LOWER(category) LIKE '%' || LOWER(:query) || '%'
""")
    fun searchProducts(query: String): Flow<List<ProductEntity>>

    @Query("SELECT DISTINCT category FROM products")
    suspend fun getAllCategories(): List<String>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): ProductEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Query("SELECT * FROM products WHERE id IN (:ids)")
    suspend fun getProductsByIds(ids: List<Int>): List<ProductEntity>

}