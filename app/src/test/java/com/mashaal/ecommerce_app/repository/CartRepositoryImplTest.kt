package com.mashaal.ecommerce_app.repository

import app.cash.turbine.test
import com.mashaal.ecommerce_app.TestUtils
import com.mashaal.ecommerce_app.data.repository.CartRepositoryImpl
import com.mashaal.ecommerce_app.domain.repository.CartRepository
import com.mashaal.ecommerce_app.fakeimpls.fake_daos.FakeCartDao
import com.mashaal.ecommerce_app.fakeimpls.fake_daos.FakeProductDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CartRepositoryImplTest {

    private lateinit var cartRepository: CartRepository
    private lateinit var fakeCartDao: FakeCartDao
    private lateinit var fakeProductDao: FakeProductDao

    @Before
    fun setup() {
        fakeCartDao = FakeCartDao()
        fakeProductDao = FakeProductDao()
        cartRepository = CartRepositoryImpl(
            cartDao = fakeCartDao,
            productDao = fakeProductDao
        )
    }

    @Test
    fun addToCart_insertsItemCorrectly() = runTest {
        cartRepository.addToCart(productId = 1, quantity = 2)
        assertEquals(1, fakeCartDao.getCartItemCount())
        assertEquals(2, fakeCartDao.getTotalQuantity())
    }

    @Test
    fun removeFromCart_deletesItem() = runTest {
        cartRepository.addToCart(productId = 1, quantity = 2)
        cartRepository.removeFromCart(productId = 1)
        assertEquals(0, fakeCartDao.getCartItemCount())
    }

    @Test
    fun removeAllFromCart_deletesAllItems() = runTest {
        cartRepository.addToCart(productId = 1, quantity = 1)
        cartRepository.addToCart(productId = 2, quantity = 1)
        cartRepository.removeAllFromCart()
        assertEquals(0, fakeCartDao.getCartItemCount())
        assertEquals(0, fakeCartDao.getTotalQuantity())
    }

    @Test
    fun updateQuantity_updatesCorrectly() = runTest {
        cartRepository.addToCart(productId = 1, quantity = 1)
        cartRepository.updateQuantity(productId = 1, quantity = 5)
        assertEquals(5, fakeCartDao.getTotalQuantity())
    }

    @Test
    fun getTotalQuantity_returnsCorrectSum() = runTest {
        cartRepository.addToCart(productId = 1, quantity = 2)
        cartRepository.addToCart(productId = 2, quantity = 3)
        assertEquals(5, cartRepository.getTotalQuantity())
    }

    @Test
    fun isProductInCart_returnsTrueIfExists() = runTest {
        cartRepository.addToCart(productId = 1, quantity = 1)
        assertTrue(cartRepository.isProductInCart(1))
    }

    @Test
    fun isProductInCart_returnsFalseIfNotExists() = runTest {
        assertFalse(cartRepository.isProductInCart(99))
    }

    @Test
    fun getCart_returnsCorrectCartItems() = runTest {
        fakeProductDao.insertProduct(TestUtils.testProduct(id = 1, name = "Apple", price = 5.0))
        fakeProductDao.insertProduct(TestUtils.testProduct(id = 2, name = "Banana", price = 3.0))
        cartRepository.addToCart(productId = 1, quantity = 1)
        cartRepository.addToCart(productId = 2, quantity = 1)
        cartRepository.getCart().test{
            val result = awaitItem()
            assertEquals(2, result.items.size)
            assertEquals("Apple", result.items[0].product.name)
            assertEquals("Banana", result.items[1].product.name)
            assertEquals(8.0, result.totalPrice)
        }

    }

    @Test
    fun getCartItemCount_returnsCorrectCount() = runTest {
        cartRepository.addToCart(productId = 1, quantity = 1)
        cartRepository.addToCart(productId = 2, quantity = 1)
        assertEquals(2, cartRepository.getCartItemCount())
    }
}
