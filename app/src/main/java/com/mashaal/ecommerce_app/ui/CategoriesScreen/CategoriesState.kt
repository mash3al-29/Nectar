package com.mashaal.ecommerce_app.ui.CategoriesScreen

data class CategoriesState(
    val categories: List<String> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: String? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

sealed class CategoriesEvent {
    data class OnSearchQueryChange(val query: String) : CategoriesEvent()
    data class OnCategorySelected(val category: String) : CategoriesEvent()
}