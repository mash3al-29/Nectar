package com.mashaal.ecommerce_app.ui.MainScreen

import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.domain.model.Product

sealed class MainScreenState {
    data object Loading : MainScreenState()
    data class Success(
        val exclusiveOffers: List<Product> = emptyList(),
        val bestSelling: List<Product> = emptyList(),
        val groceries: List<Product> = emptyList(),
        val searchResults: List<Product> = emptyList(),
        val isSearching: Boolean = false,
        val searchQuery: String = "",
        val selectedCategory: String? = null,
        val locationResId: Int = R.string.default_location,
        val selectedTabIndex: Int = 0,
    ) : MainScreenState() {
        val isSearchActive: Boolean
            get() = searchQuery.isNotBlank()
    }
    data class Error(val message: String) : MainScreenState()
}

sealed class MainScreenEvent {
    data class OnSearchQueryChange(val query: String) : MainScreenEvent()
    data class OnAddToCartClicked(val product: Product) : MainScreenEvent()
}