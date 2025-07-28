package com.mashaal.ecommerce_app.ui.MainScreen

import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.domain.model.Product

data class MainScreenState(
    val exclusiveOffers: List<Product> = emptyList(),
    val bestSelling: List<Product> = emptyList(),
    val groceries: List<Product> = emptyList(),
    val searchResults: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val isSearching: Boolean = false,
    val error: String = "",
    val searchQuery: String = "",
    val selectedCategory: String? = null,
    val locationResId: Int = R.string.default_location,
    val selectedTabIndex: Int = 0,
) {
    val isSearchActive: Boolean
        get() = searchQuery.isNotBlank()
}

sealed class MainScreenEvent {
    data class OnSearchQueryChange(val query: String) : MainScreenEvent()
    data class OnCategorySelected(val category: String) : MainScreenEvent()
    data class OnAddToCartClicked(val product: Product) : MainScreenEvent()
    data object OnShopTabClicked : MainScreenEvent()
    data object OnExploreTabClicked : MainScreenEvent()
    data object OnCartTabClicked : MainScreenEvent()
}