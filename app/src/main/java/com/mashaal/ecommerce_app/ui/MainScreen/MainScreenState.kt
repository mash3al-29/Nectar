package com.mashaal.ecommerce_app.ui.MainScreen

import com.mashaal.ecommerce_app.domain.model.Product

data class MainScreenState(
    val exclusiveOffers: List<Product> = emptyList(),
    val bestSelling: List<Product> = emptyList(),
    val groceries: List<Product> = emptyList(),
    val isLoading: Boolean = true,
    val error: String = "",
    val searchQuery: String = "",
    val selectedCategory: String? = null,
    val location: String = "Alexandria, Egypt",
    val selectedTabIndex: Int = 0
)

sealed class MainScreenEvent {
    data class OnSearchQueryChange(val query: String) : MainScreenEvent()
    data class OnCategorySelected(val category: String) : MainScreenEvent()
    data object OnAddToCartClicked : MainScreenEvent()
    data object OnShopTabClicked : MainScreenEvent()
    data object OnExploreTabClicked : MainScreenEvent()
    data object OnCartTabClicked : MainScreenEvent()
}