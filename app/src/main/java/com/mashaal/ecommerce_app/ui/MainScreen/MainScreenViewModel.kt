package com.mashaal.ecommerce_app.ui.MainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.mashaal.ecommerce_app.domain.usecase.GetAllProductsUseCase
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state.asStateFlow()
    init {
        loadMockData()
    }
    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = event.query) }
            }
            is MainScreenEvent.OnCategorySelected -> {
                _state.update { it.copy(selectedCategory = event.category) }
            }
            is MainScreenEvent.OnAddToCartClicked -> {
                // Handle add to cart
            }
            is MainScreenEvent.OnShopTabClicked -> {
                // handle shop navigation
            }
            is MainScreenEvent.OnExploreTabClicked -> {
                // handle home navigation
            }
            is MainScreenEvent.OnCartTabClicked -> {
                // Handle tab navigation
            }
        }
    }

    private fun loadMockData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            try {
                val products = getAllProductsUseCase.execute()
                _state.update { it.copy(
                     exclusiveOffers = products,
                     bestSelling = products.shuffled(),
                     groceries = products.shuffled(),
                     isLoading = false
                 )}
            } catch (e: Exception) {
                _state.update { it.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )}
            }
        }
    }
}

