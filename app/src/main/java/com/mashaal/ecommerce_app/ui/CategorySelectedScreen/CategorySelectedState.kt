package com.mashaal.ecommerce_app.ui.CategorySelectedScreen

import com.mashaal.ecommerce_app.domain.model.Product

data class CategorySelectedState(
    val categoryName: String = "",
    val products: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val selectedPriceRange: String? = null,
    val selectedProductPortions: Set<String> = emptySet()
)

sealed class CategorySelectedEvent {
    data class OnAddToCartClicked(val product: Product) : CategorySelectedEvent()
}