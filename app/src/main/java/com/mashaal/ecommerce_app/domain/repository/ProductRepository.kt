package com.mashaal.ecommerce_app.domain.repository

import com.mashaal.ecommerce_app.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProductById(id: Int): Product?
    fun getAllProducts(): Flow<List<Product>>
    suspend fun getAllCategories(): List<String>
    suspend fun getProductsByCategory(category: String): List<Product>
    suspend fun getProductsByCategoryAndPrice(category: String, minPrice: Double, maxPrice: Double): List<Product>
    suspend fun getProductsByCategoryAndDetail(category: String, detail: String): List<Product>
    suspend fun getProductsByCategoryPriceAndDetail(category: String, minPrice: Double, maxPrice: Double, detail: String): List<Product>
    suspend fun searchProducts(query: String): List<Product>
}