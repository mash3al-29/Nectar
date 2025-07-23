package com.mashaal.ecommerce_app.ui.CategorySelectedScreen

import com.mashaal.ecommerce_app.domain.model.Product

data class CategorySelectedState(
    val categoryName: String = "",
    val products: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val productPortions: List<String> = listOf("1kg", "1L", "500g", "300g", "400g", "1pc", "150g", "12pcs", "250g", "25 bags", "500ml"),
    val selectedPriceRange: String? = null,
    val selectedProductPortions: Set<String> = emptySet(),
    val showFilterBottomSheet: Boolean = false
)

sealed class CategorySelectedEvent {
    data object OnFilterClicked : CategorySelectedEvent()
    data object OnAddToCartClicked : CategorySelectedEvent()
    data class OnPriceRangeSelected(val priceRange: String?) : CategorySelectedEvent()
    data class OnProductPortionSelected(val portion: String, val selected: Boolean) : CategorySelectedEvent()
    data object OnApplyFilter : CategorySelectedEvent()
}