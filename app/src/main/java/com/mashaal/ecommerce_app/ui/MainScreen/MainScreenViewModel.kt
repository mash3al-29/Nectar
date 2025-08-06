package com.mashaal.ecommerce_app.ui.MainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashaal.ecommerce_app.domain.extensions.groceries
import com.mashaal.ecommerce_app.domain.extensions.isBestSelling
import com.mashaal.ecommerce_app.domain.extensions.isExclusiveOffer
import com.mashaal.ecommerce_app.domain.model.Cart
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.domain.usecase.AddToCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.GetAllProductsUseCase
import com.mashaal.ecommerce_app.domain.usecase.GetCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.SearchProductsUseCase
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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class MainScreenViewModel @Inject constructor(
    getAllProductsUseCase: GetAllProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val searchProductsUseCase: SearchProductsUseCase,
    getCartUseCase: GetCartUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _isSearching = MutableStateFlow(false)
    private val _isInitialLoading = MutableStateFlow(true)

    val cartProductIds: StateFlow<Set<Int>> = getCartUseCase()
        .catch { emit(Cart(emptyList())) }
        .map { cart -> cart.items.map { it.product.id }.toSet() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    private val allProductsFlow = getAllProductsUseCase()
        .catch { emit(emptyList()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            delay(LOADING_DELAY_MS)
            _isInitialLoading.value = false
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 300L
        private const val LOADING_DELAY_MS = 1500L
    }

    private val searchResultsFlow = _searchQuery
        .debounce(SEARCH_DEBOUNCE_MS)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                _isSearching.value = false
                flowOf(emptyList<Product>())
            } else {
                _isSearching.value = true
                searchProductsUseCase(query.trim())
                    .catch { 
                        _isSearching.value = false
                        emit(emptyList()) 
                    }
                    .map { results ->
                        _isSearching.value = false
                        results
                    }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val state: StateFlow<MainScreenState> = combine(
        _isInitialLoading,
        allProductsFlow,
        searchResultsFlow,
        _searchQuery,
        _isSearching
    ) { isLoading, allProducts, searchResults, query, isSearching ->
        if (isLoading) {
            MainScreenState.Loading
        } else {
            val exclusiveOffers = allProducts.filter { it.isExclusiveOffer }
            val bestSelling = allProducts.filter { it.isBestSelling }  
            val groceries = allProducts.filter { it.groceries }
            
            MainScreenState.Success(
                exclusiveOffers = exclusiveOffers,
                bestSelling = bestSelling,
                groceries = groceries,
                searchResults = searchResults,
                searchQuery = query,
                isSearching = isSearching
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MainScreenState.Loading
    )

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OnSearchQueryChange -> {
                _searchQuery.value = event.query
            }
            is MainScreenEvent.OnAddToCartClicked -> {
                addToCart(event.product)
            }
        }
    }

    private fun addToCart(product: Product) {
        viewModelScope.launch {
            try {
                addToCartUseCase(
                    productId = product.id,
                    quantity = 1
                )
            } catch (e: Exception) {
                println("Failed to add product to cart: ${e.message}")
            }
        }
    }
}

