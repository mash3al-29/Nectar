package com.mashaal.ecommerce_app.domain.repository

import com.mashaal.ecommerce_app.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProductById(id: Int): Product?
    fun getAllProducts(): Flow<List<Product>>
    suspend fun getAllCategories(): List<String>
    fun getProductsByCategory(category: String): Flow<List<Product>>
    fun getProductsByCategoryAndPrice(category: String, minPrice: Double, maxPrice: Double): Flow<List<Product>>
    fun getProductsByCategoryAndDetail(category: String, detail: String): Flow<List<Product>>
    fun getProductsByCategoryPriceAndDetail(category: String, minPrice: Double, maxPrice: Double, detail: String): Flow<List<Product>>
    fun searchProducts(query: String): Flow<List<Product>>
}