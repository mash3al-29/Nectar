package com.mashaal.ecommerce_app.viewmodel

import app.cash.turbine.test
import com.mashaal.ecommerce_app.TestUtils
import com.mashaal.ecommerce_app.domain.model.Cart
import com.mashaal.ecommerce_app.domain.usecase.GetCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.RemoveAllFromCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.RemoveFromCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.UpdateCartQuantityUseCase
import com.mashaal.ecommerce_app.ui.MyCartScreen.MyCartScreenEvent
import com.mashaal.ecommerce_app.ui.MyCartScreen.MyCartScreenState
import com.mashaal.ecommerce_app.ui.MyCartScreen.MyCartScreenViewModel
import com.mashaal.ecommerce_app.ui.MyCartScreen.toUIModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class MyCartScreenViewModelTest {
    private lateinit var getCartUseCase: GetCartUseCase
    private lateinit var removeFromCartUseCase: RemoveFromCartUseCase
    private lateinit var updateCartQuantityUseCase: UpdateCartQuantityUseCase
    private lateinit var removeAllFromCartUseCase: RemoveAllFromCartUseCase
    private lateinit var viewModel: MyCartScreenViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getCartUseCase = mockk()
        removeFromCartUseCase = mockk(relaxed = true)
        updateCartQuantityUseCase = mockk(relaxed = true)
        removeAllFromCartUseCase = mockk(relaxed = true)
    }

    @Test
    fun `initial state emits Success when cart is returned`() = runTest {
        val fakeCart = Cart(
            items = listOf(TestUtils.testCartItem(productId = 1, quantity = 2)),
            totalPrice = 10.0
        )
        every { getCartUseCase.invoke() } returns flowOf(fakeCart)
        viewModel = MyCartScreenViewModel(
            getCartUseCase,
            removeFromCartUseCase,
            updateCartQuantityUseCase,
            removeAllFromCartUseCase
        )
        viewModel.state.test {
            assertEquals(
                MyCartScreenState.Success(
                cartItems = fakeCart.items.map { it.toUIModel() },
                totalPrice = 10.0
            ), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onEvent OnQuantityChanged triggers updateCartQuantityUseCase`() = runTest {
        every { getCartUseCase.invoke() } returns flowOf(Cart(emptyList(), 0.0))
        coEvery { updateCartQuantityUseCase(1, 3) } just Runs
        viewModel = MyCartScreenViewModel(
            getCartUseCase,
            removeFromCartUseCase,
            updateCartQuantityUseCase,
            removeAllFromCartUseCase
        )
        viewModel.onEvent(MyCartScreenEvent.OnQuantityChanged(productId = 1, quantity = 3))
        coVerify { updateCartQuantityUseCase(1, 3) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
