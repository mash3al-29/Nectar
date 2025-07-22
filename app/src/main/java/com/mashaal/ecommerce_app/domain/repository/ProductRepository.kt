package com.mashaal.ecommerce_app.domain.repository

import com.mashaal.ecommerce_app.domain.model.Product

interface ProductRepository {
    suspend fun getProductById(id: Int): Product?
    suspend fun getAllProducts(): List<Product>
}