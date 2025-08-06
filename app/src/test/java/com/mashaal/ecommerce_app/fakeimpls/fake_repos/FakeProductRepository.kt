package com.mashaal.ecommerce_app.fake

import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.domain.model.toDomain
import com.mashaal.ecommerce_app.domain.repository.ProductRepository
import com.mashaal.ecommerce_app.fakeimpls.fake_daos.FakeProductDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FakeProductRepository(
    private val productDao: FakeProductDao
) : ProductRepository {

    override suspend fun getProductById(id: Int) = productDao.getProductById(id)?.toDomain()

    override fun getAllProducts(): Flow<List<Product>> =
        productDao.getAllProducts().map { list -> list.map { it.toDomain() } }

    override suspend fun getAllCategories(): List<String> = productDao.getAllCategories()

    override fun getProductsByCategory(category: String): Flow<List<Product>> =
        productDao.getProductsByCategory(category).map { list -> list.map { it.toDomain() } }

    override fun getProductsByCategoryAndPrice(
        category: String,
        minPrice: Double,
        maxPrice: Double
    ): Flow<List<Product>> =
        productDao.getProductsByCategoryAndPrice(category, minPrice, maxPrice)
            .map { it -> it.map { it.toDomain() } }

    override fun getProductsByCategoryAndDetail(category: String, detail: String): Flow<List<Product>> =
        productDao.getProductsByCategoryAndDetail(category, detail)
            .map { it -> it.map { it.toDomain() } }


    override fun getProductsByCategoryPriceAndDetail(
        category: String,
        minPrice: Double,
        maxPrice: Double,
        detail: String
    ): Flow<List<Product>> =
        productDao.getProductsByCategoryPriceAndDetail(category, minPrice, maxPrice, detail)
            .map { it -> it.map { it.toDomain() } }

    override fun searchProducts(query: String): Flow<List<Product>> =
        productDao.searchProducts(query).map { it -> it.map { it.toDomain() } }
}

