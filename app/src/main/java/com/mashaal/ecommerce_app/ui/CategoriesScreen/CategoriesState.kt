package com.mashaal.ecommerce_app.ui.CategoriesScreen

import com.mashaal.ecommerce_app.domain.model.Product

sealed class CategoriesState {
    data object Loading : CategoriesState()
    data class Success(
        val categories: List<String> = emptyList(),
        val searchResults: List<Product> = emptyList(),
        val searchQuery: String = "",
        val selectedCategory: String? = null,
        val isSearching: Boolean = false
    ) : CategoriesState()
    data class Error(val message: String) : CategoriesState()
}

sealed class CategoriesEvent {
    data class OnSearchQueryChange(val query: String) : CategoriesEvent()
    data class OnAddToCartClicked(val product: Product) : CategoriesEvent()
}