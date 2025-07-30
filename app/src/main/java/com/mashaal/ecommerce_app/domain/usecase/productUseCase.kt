package com.mashaal.ecommerce_app.domain.usecase

import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(productId: Int): Product? {
        return repository.getProductById(productId)
    }
}

class GetAllProductsUseCase @Inject constructor(private val repository: ProductRepository) {
    operator fun invoke(): Flow<List<Product>> {
        return repository.getAllProducts()
    }
}

class GetAllCategoriesUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(): List<String> {
        return repository.getAllCategories()
    }
}

class GetProductsByCategoryUseCase @Inject constructor(private val repository: ProductRepository) {
    operator fun invoke(category: String): Flow<List<Product>> {
        return repository.getProductsByCategory(category)
    }
}