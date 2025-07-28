package com.mashaal.ecommerce_app.ui.MainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashaal.ecommerce_app.domain.extensions.groceries
import com.mashaal.ecommerce_app.domain.extensions.isBestSelling
import com.mashaal.ecommerce_app.domain.extensions.isExclusiveOffer
import com.mashaal.ecommerce_app.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.mashaal.ecommerce_app.domain.usecase.GetAllProductsUseCase
import com.mashaal.ecommerce_app.domain.usecase.AddToCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.SearchProductsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val searchProductsUseCase: SearchProductsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state.asStateFlow()

    private var hasInitialDataLoaded = false
    
    init {
        loadDataIfNeeded()
    }
    
    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = event.query) }
                performSearch(event.query)
            }
            is MainScreenEvent.OnCategorySelected -> {
                _state.update { it.copy(selectedCategory = event.category) }
            }
            is MainScreenEvent.OnAddToCartClicked -> {
                addToCart(event.product)
            }
            is MainScreenEvent.OnShopTabClicked -> {
                _state.update { it.copy(selectedTabIndex = 0) }
            }
            is MainScreenEvent.OnExploreTabClicked -> {
                _state.update { it.copy(selectedTabIndex = 1) }
            }
            is MainScreenEvent.OnCartTabClicked -> {
                _state.update { it.copy(selectedTabIndex = 2) }
            }
        }
    }

    private fun loadDataIfNeeded() {
        if (!hasInitialDataLoaded) {
            loadData()
        } else {
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            delay(500)
                getAllProductsUseCase.execute().catch { e ->
                    _state.update { it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error occurred"
                    )}
                }.collect { productsList ->
                    val exclusiveOffers = productsList.filter { it.isExclusiveOffer }
                    val bestSelling = productsList.filter { it.isBestSelling }
                    val groceries = productsList.filter { it.groceries }

                    _state.update { it.copy(
                        exclusiveOffers = exclusiveOffers,
                        bestSelling = bestSelling,
                        groceries = groceries,
                        isLoading = false
                    )}
                    hasInitialDataLoaded = true
                }
        }
    }

    private fun addToCart(product: Product) {
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
}

