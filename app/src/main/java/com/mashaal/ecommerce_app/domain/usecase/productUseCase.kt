package com.mashaal.ecommerce_app.domain.usecase

import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend fun execute(productId: Int): Product? {
        return repository.getProductById(productId)
    }
}

class GetAllProductsUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend fun execute(): List<Product> {
        return repository.getAllProducts()
    }
}