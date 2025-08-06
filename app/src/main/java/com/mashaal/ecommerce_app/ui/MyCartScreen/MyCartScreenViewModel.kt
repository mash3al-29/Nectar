package com.mashaal.ecommerce_app.ui.MyCartScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashaal.ecommerce_app.domain.model.Cart
import com.mashaal.ecommerce_app.domain.usecase.GetCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.RemoveAllFromCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.RemoveFromCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.UpdateCartQuantityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCartScreenViewModel @Inject constructor(
    getCartUseCase: GetCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val updateCartQuantityUseCase: UpdateCartQuantityUseCase,
    private val removeAllFromCartUseCase: RemoveAllFromCartUseCase
) : ViewModel() {

    val state: StateFlow<MyCartScreenState> = getCartUseCase()
        .map<Cart, MyCartScreenState> { cart ->
            MyCartScreenState.Success(
                cartItems = cart.items.map { it.toUIModel() },
                totalPrice = cart.totalPrice
            )
        }
        .catch { 
            emit(MyCartScreenState.Error(it.message ?: "Failed to load cart")) 
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MyCartScreenState.Loading
        )

    fun onEvent(event: MyCartScreenEvent) {
        when (event) {
            is MyCartScreenEvent.OnQuantityChanged -> {
                updateQuantity(event.productId, event.quantity)
            }
            is MyCartScreenEvent.OnRemoveItem -> {
                removeItem(event.productId)
            }
            is MyCartScreenEvent.OnCheckoutClicked -> {
                removeAllItems()
            }
        }
    }

    private fun updateQuantity(productId: Int, quantity: Int) {
        viewModelScope.launch {
            try {
                updateCartQuantityUseCase(productId, quantity)
            } catch (e: Exception) {
                println("Failed to update cart quantity: ${e.message}")
            }
        }
    }

    private fun removeItem(productId: Int) {
        viewModelScope.launch {
            try {
                removeFromCartUseCase(productId)
            } catch (e: Exception) {
                println("Failed to remove item from cart: ${e.message}")
            }
        }
    }

    private fun removeAllItems() {
        viewModelScope.launch {
            try {
                removeAllFromCartUseCase()
            } catch (e: Exception) {
                println("Failed to clear cart: ${e.message}")
            }
        }
    }
}