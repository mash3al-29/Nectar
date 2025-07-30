package com.mashaal.ecommerce_app.ui.CategoriesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashaal.ecommerce_app.domain.model.Cart
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.domain.usecase.GetAllCategoriesUseCase
import com.mashaal.ecommerce_app.domain.usecase.SearchProductsUseCase
import com.mashaal.ecommerce_app.domain.usecase.AddToCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.GetCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val searchProductsUseCase: SearchProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    getCartUseCase: GetCartUseCase
) : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    private val _isInitialLoading = MutableStateFlow(true)

    init {
        viewModelScope.launch {
            try {
                delay(LOADING_DELAY_MS)
                val result = getAllCategoriesUseCase()
                _categories.value = result
                _isInitialLoading.value = false
            } catch (e: Exception) {
                println("Failed to load categories: ${e.message}")
                _categories.value = emptyList()
                _isInitialLoading.value = false
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 300L
        private const val LOADING_DELAY_MS = 1500L
    }


    val cartProductIds: StateFlow<Set<Int>> = getCartUseCase()
        .catch { emit(Cart(emptyList())) }
        .map { cart -> cart.items.map { it.product.id }.toSet() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    private val searchResultsFlow = _searchQuery
        .debounce(SEARCH_DEBOUNCE_MS)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                kotlinx.coroutines.flow.flowOf(emptyList())
            } else {
                searchProductsUseCase(query.trim())
                    .catch { emit(emptyList()) }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val state: StateFlow<CategoriesState> = combine(
        _categories,
        searchResultsFlow,
        _searchQuery,
        _isInitialLoading
    ) { categories, searchResults, query, isLoading ->
        if (isLoading) {
            CategoriesState.Loading
        } else {
            CategoriesState.Success(
                categories = categories,
                searchResults = searchResults,
                searchQuery = query
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CategoriesState.Loading
    )


    fun onEvent(event: CategoriesEvent) {
        when (event) {
            is CategoriesEvent.OnSearchQueryChange -> {
                _searchQuery.value = event.query
            }
            is CategoriesEvent.OnAddToCartClicked -> {
                addToCart(event.product)
            }
        }
    }

    private fun addToCart(product: Product) {
        viewModelScope.launch {
            try {
                addToCartUseCase(
                    productId = product.id,
                    quantity = 1,
                    portion = product.detail
                )
            } catch (e: Exception) {
                println("Failed to add product to cart: ${e.message}")
            }
        }
    }
}