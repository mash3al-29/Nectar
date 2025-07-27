package com.mashaal.ecommerce_app.ui.MyCartScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashaal.ecommerce_app.domain.usecase.GetCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.RemoveAllFromCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.RemoveFromCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.UpdateCartQuantityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyCartScreenViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val updateCartQuantityUseCase: UpdateCartQuantityUseCase,
    private val removeAllFromCartUseCase: RemoveAllFromCartUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MyCartScreenState())
    val state: StateFlow<MyCartScreenState> = _state.asStateFlow()

    init {
        loadCart()
    }

    fun onEvent(event: MyCartScreenEvent) {
        when (event) {
            is MyCartScreenEvent.OnQuantityChanged -> {
                updateQuantity(event.productId, event.quantity)
            }
            is MyCartScreenEvent.OnRemoveItem -> {
                removeItem(event.productId)
            }
            is MyCartScreenEvent.OnCheckoutClicked -> {
                removeAllFromCart()
                println("Proceeding to checkout with total: $${state.value.totalPrice}")
            }
        }
    }

    private fun removeAllFromCart(){
        viewModelScope.launch {
            try {
                removeAllFromCartUseCase.execute()
            } catch (e: Exception) {
                _state.update {
                    it.copy(error = e.message ?: "Failed to remove item")
                }
            }
        }
    }

    private fun loadCart() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getCartUseCase.execute()
                .catch { error ->
                    _state.update { 
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Failed to load cart"
                        )
                    }
                }
                .collect { cart ->
                    _state.update { currentState ->
                        currentState.copy(
                            cartItems = cart.items.map { it.toUIModel() },
                            totalPrice = cart.totalPrice,
                            isLoading = false,
                            error = null
                        )
                    }
                }
        }
    }

    private fun updateQuantity(productId: Int, quantity: Int) {
        viewModelScope.launch {
            try {
                updateCartQuantityUseCase.execute(productId, quantity)
            } catch (e: Exception) {
                _state.update { 
                    it.copy(error = e.message ?: "Failed to update quantity")
                }
            }
        }
    }

    private fun removeItem(productId: Int) {
        viewModelScope.launch {
            try {
                removeFromCartUseCase.execute(productId)
            } catch (e: Exception) {
                _state.update { 
                    it.copy(error = e.message ?: "Failed to remove item")
                }
            }
        }
    }
}