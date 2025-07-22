package com.mashaal.ecommerce_app.ui.ProductScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashaal.ecommerce_app.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.mashaal.ecommerce_app.domain.repository.ProductRepository
import com.mashaal.ecommerce_app.domain.usecase.GetProductByIdUseCase
import javax.inject.Inject

@HiltViewModel
class ProductScreenViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ProductScreenState())
    val state: StateFlow<ProductScreenState> = _state.asStateFlow()

    fun onEvent(event: ProductScreenEvent) {
        when (event) {
            is ProductScreenEvent.OnQuantityChanged -> {
                _state.update { it.copy(quantity = event.quantity) }
            }
            is ProductScreenEvent.OnFavoriteToggled -> {
                _state.update { it.copy(isFavorite = !it.isFavorite) }
            }
            is ProductScreenEvent.OnAddToCartClicked -> {
                // Handle add to cart logic
                // This could involve adding the product to a cart repository
                // or updating a cart state in another ViewModel
            }
            is ProductScreenEvent.OnBackClicked -> {
                // Navigation is handled by the caller
            }
        }
    }
    
    fun loadProductById(productId: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val product = getProductByIdUseCase.execute(productId)
                if (product != null) {
                    _state.update { it.copy(
                        product = product,
                        isLoading = false
                    ) }
                } else {
                    _state.update { it.copy(
                        isLoading = false,
                        error = "Product not found"
                    ) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                ) }
            }
        }
    }
}
