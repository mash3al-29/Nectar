package com.mashaal.ecommerce_app.domain.usecase

import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.domain.repository.ProductRepository
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    suspend fun execute(query: String): List<Product> {
        return if (query.isBlank()) {
            emptyList()
        } else {
            productRepository.searchProducts(query.trim())
        }
    }
} 