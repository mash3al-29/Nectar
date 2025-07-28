package com.mashaal.ecommerce_app.ui.CategoriesScreen

import com.mashaal.ecommerce_app.domain.model.Product

data class CategoriesState(
    val categories: List<String> = emptyList(),
    val searchResults: List<Product> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: String? = null,
    val isLoading: Boolean = false,
    val isSearching: Boolean = false,
    val error: String? = null
) {
    val isSearchActive: Boolean
        get() = searchQuery.isNotBlank()
}

sealed class CategoriesEvent {
    data class OnSearchQueryChange(val query: String) : CategoriesEvent()
    data class OnCategorySelected(val category: String) : CategoriesEvent()
    data class OnAddToCartClicked(val product: Product) : CategoriesEvent()
}