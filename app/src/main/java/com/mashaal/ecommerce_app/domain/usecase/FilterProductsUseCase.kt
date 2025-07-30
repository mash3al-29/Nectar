package com.mashaal.ecommerce_app.domain.usecase

import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsByCategoryAndPriceUseCase @Inject constructor(private val repository: ProductRepository) {
    operator fun invoke(category: String, minPrice: Double, maxPrice: Double): Flow<List<Product>> {
        return repository.getProductsByCategoryAndPrice(category, minPrice, maxPrice)
    }
}

class GetProductsByCategoryAndDetailUseCase @Inject constructor(private val repository: ProductRepository) {
    operator fun invoke(category: String, detail: String): Flow<List<Product>> {
        return repository.getProductsByCategoryAndDetail(category, detail)
    }
}

class GetProductsByCategoryPriceAndDetailUseCase @Inject constructor(private val repository: ProductRepository) {
    operator fun invoke(category: String, minPrice: Double, maxPrice: Double, detail: String): Flow<List<Product>> {
        return repository.getProductsByCategoryPriceAndDetail(category, minPrice, maxPrice, detail)
    }
}
