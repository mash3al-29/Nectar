package com.mashaal.ecommerce_app.data.repository

import com.mashaal.ecommerce_app.data.Dao.ProductDao
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao
) : ProductRepository {
    override suspend fun getProductById(id: Int): Product? {
        val productEntity = productDao.getProductById(id)
        return productEntity?.toDomainModel()
    }
    
    override fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getAllCategories(): List<String> {
        return productDao.getAllCategories()
    }

    override fun getProductsByCategory(category: String): Flow<List<Product>> {
        return productDao.getProductsByCategory(category).map { it.map { entity -> entity.toDomainModel() } }
    }

    override fun getProductsByCategoryAndPrice(category: String, minPrice: Double, maxPrice: Double): Flow<List<Product>> {
        return productDao.getProductsByCategoryAndPrice(category, minPrice, maxPrice)
            .map { it.map { entity -> entity.toDomainModel() } }
    }

    override fun getProductsByCategoryAndDetail(category: String, detail: String): Flow<List<Product>> {
        return productDao.getProductsByCategoryAndDetail(category, detail)
            .map { it.map { entity -> entity.toDomainModel() } }
    }

    override fun getProductsByCategoryPriceAndDetail(
        category: String,
        minPrice: Double,
        maxPrice: Double,
        detail: String
    ): Flow<List<Product>> {
        return productDao.getProductsByCategoryPriceAndDetail(category, minPrice, maxPrice, detail)
            .map { it.map { entity -> entity.toDomainModel() } }
    }

    override fun searchProducts(query: String): Flow<List<Product>> {
        return productDao.searchProducts(query).map { it -> it.map { it.toDomainModel() } }
    }
}