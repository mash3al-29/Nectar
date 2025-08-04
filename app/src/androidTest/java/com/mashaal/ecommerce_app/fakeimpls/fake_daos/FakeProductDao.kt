package com.mashaal.ecommerce_app.fakeimpls.fake_daos

import com.mashaal.ecommerce_app.data.Dao.ProductDao
import com.mashaal.ecommerce_app.data.Entity.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeProductDaoUI : ProductDao {

    private val productList = mutableListOf<ProductEntity>()
    private val productFlow = MutableStateFlow<List<ProductEntity>>(emptyList())

    override fun getAllProducts(): Flow<List<ProductEntity>> = productFlow

    override fun getProductsByCategory(category: String): Flow<List<ProductEntity>> {
        return productFlow.map { list ->
            list.filter { it.category.equals(category, ignoreCase = true) }
        }
    }

    override fun getProductsByCategoryAndPrice(category: String, minPrice: Double, maxPrice: Double): Flow<List<ProductEntity>> {
        return productFlow.map { list ->
            list.filter {
                it.category.equals(category, ignoreCase = true) &&
                        it.price in minPrice..maxPrice
            }
        }
    }

    override fun getProductsByCategoryAndDetail(category: String, detail: String): Flow<List<ProductEntity>> {
        return productFlow.map { list ->
            list.filter {
                it.category.equals(category, ignoreCase = true) &&
                        it.detail.contains(detail, ignoreCase = true)
            }
        }
    }

    override fun getProductsByCategoryPriceAndDetail(
        category: String,
        minPrice: Double,
        maxPrice: Double,
        detail: String
    ): Flow<List<ProductEntity>> {
        return productFlow.map { list ->
            list.filter {
                it.category.equals(category, ignoreCase = true) &&
                        it.price in minPrice..maxPrice &&
                        it.detail.contains(detail, ignoreCase = true)
            }
        }
    }

    override fun searchProducts(query: String): Flow<List<ProductEntity>> {
        return productFlow.map { list ->
            list.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true) ||
                        it.category.contains(query, ignoreCase = true)
            }
        }
    }

    override suspend fun getAllCategories(): List<String> {
        return productList.map { it.category }.distinct()
    }

    override suspend fun getProductById(id: Int): ProductEntity? {
        return productList.find { it.id == id }
    }

    override suspend fun insertProducts(products: List<ProductEntity>) {
        productList.removeAll { existing -> products.any { it.id == existing.id } }
        productList.addAll(products)
        productFlow.value = productList.toList()
    }

    override suspend fun insertProduct(product: ProductEntity) {
        productList.removeAll { it.id == product.id }
        productList.add(product)
        productFlow.value = productList.toList()
    }

    override suspend fun getProductsByIds(ids: List<Int>): List<ProductEntity> {
        return productList.filter { it.id in ids }
    }
} 