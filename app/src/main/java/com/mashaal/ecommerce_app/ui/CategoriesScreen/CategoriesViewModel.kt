package com.mashaal.ecommerce_app.ui.CategoriesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashaal.ecommerce_app.domain.usecase.GetAllCategoriesUseCase
import com.mashaal.ecommerce_app.domain.usecase.SearchProductsUseCase
import com.mashaal.ecommerce_app.domain.usecase.AddToCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val searchProductsUseCase: SearchProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CategoriesState())
    val state: StateFlow<CategoriesState> = _state.asStateFlow()

    init {
        loadCategories()
    }

    fun onEvent(event: CategoriesEvent) {
        when (event) {
            is CategoriesEvent.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = event.query) }
                performSearch(event.query)
            }
            is CategoriesEvent.OnCategorySelected -> {
                _state.update { it.copy(selectedCategory = event.category) }
            }
            is CategoriesEvent.OnAddToCartClicked -> {
                addToCart(event.product)
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = getAllCategoriesUseCase.execute()
                _state.update { it.copy(
                    categories = categories,
                    isLoading = false
                ) }
            } catch (e: Exception) {
                _state.update { it.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load categories"
                ) }
            }
        }
    }

    private fun performSearch(query: String) {
        if (query.isBlank()) {
            _state.update { it.copy(searchResults = emptyList(), isSearching = false) }
            return
        }

        viewModelScope.launch {
            try {
                _state.update { it.copy(isSearching = true) }
                val results = searchProductsUseCase.execute(query)
                _state.update { it.copy(
                    searchResults = results,
                    isSearching = false
                ) }
            } catch (e: Exception) {
                _state.update { it.copy(
                    searchResults = emptyList(),
                    isSearching = false,
                    error = e.message ?: "Search failed"
                ) }
            }
        }
    }

    private fun addToCart(product: com.mashaal.ecommerce_app.domain.model.Product) {
        viewModelScope.launch {
            try {
                addToCartUseCase.execute(
                    productId = product.id,
                    quantity = 1,
                    portion = product.detail
                )
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message ?: "Failed to add to cart") }
            }
        }
    }
}