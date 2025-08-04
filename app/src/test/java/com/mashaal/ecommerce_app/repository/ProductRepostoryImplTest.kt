package com.mashaal.ecommerce_app.data.repository

import app.cash.turbine.test
import com.mashaal.ecommerce_app.TestUtils
import com.mashaal.ecommerce_app.fakeimpls.fake_daos.FakeProductDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ProductRepositoryImplTest {

    private lateinit var fakeProductDao: FakeProductDao
    private lateinit var productRepository: ProductRepositoryImpl

    @Before
    fun setup() {
        fakeProductDao = FakeProductDao()
        productRepository = ProductRepositoryImpl(fakeProductDao)
    }

    @Test
    fun `getProductById returns correct product`() = runTest {
        val product = TestUtils.testProduct(id = 1, name = "Shampoo")
        fakeProductDao.insertProduct(product)

        val result = productRepository.getProductById(1)

        assertNotNull(result)
        assertEquals("Shampoo", result.name)
    }

    @Test
    fun `getAllProducts returns all inserted products`() = runTest {
        val products = listOf(
            TestUtils.testProduct(id = 1, name = "Apple"),
            TestUtils.testProduct(id = 2, name = "Orange")
        )
        fakeProductDao.insertProducts(products)

        productRepository.getAllProducts().test {
            val result = awaitItem()
            assertEquals(2, result.size)
            assertTrue(result.any { it.name == "Apple" })
            assertTrue(result.any { it.name == "Orange" })
            cancel()
        }
    }

    @Test
    fun `getProductsByCategory filters correctly`() = runTest {
        val products = listOf(
            TestUtils.testProduct(id = 1, category = "Fruits"),
            TestUtils.testProduct(id = 2, category = "Vegetables")
        )
        fakeProductDao.insertProducts(products)

        productRepository.getProductsByCategory("Fruits").test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("Fruits", result[0].category)
            cancel()
        }
    }

    @Test
    fun `getAllCategories returns distinct categories`() = runTest {
        val products = listOf(
            TestUtils.testProduct(id = 1, category = "Snacks"),
            TestUtils.testProduct(id = 2, category = "Snacks"),
            TestUtils.testProduct(id = 3, category = "Drinks")
        )
        fakeProductDao.insertProducts(products)

        val categories = productRepository.getAllCategories()
        assertEquals(2, categories.size)
        assertTrue(categories.contains("Snacks"))
        assertTrue(categories.contains("Drinks"))
    }

    @Test
    fun `searchProducts returns matching results`() = runTest {
        val products = listOf(
            TestUtils.testProduct(id = 1, name = "Banana Smoothie", category = "Drinks", description = "Cold drink"),
            TestUtils.testProduct(id = 2, name = "Apple Juice", category = "Drinks", description = "Fresh")
        )
        fakeProductDao.insertProducts(products)

        productRepository.searchProducts("smoothie").test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals("Banana Smoothie", result[0].name)
            cancel()
        }
    }
}
