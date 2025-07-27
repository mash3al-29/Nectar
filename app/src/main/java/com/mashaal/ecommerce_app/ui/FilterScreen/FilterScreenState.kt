package com.mashaal.ecommerce_app.ui.FilterScreen

data class FilterScreenState(
    val categoryName: String = "",
    val productPortions: List<String> = listOf("1kg", "1L", "500g", "300g", "400g", "1pc", "150g", "12pcs", "250g", "25 bags", "500ml"),
    val selectedPriceRange: String? = null,
    val selectedProductPortions: Set<String> = emptySet()
)

sealed class FilterScreenEvent {
    data class OnPriceRangeSelected(val priceRange: String?) : FilterScreenEvent()
    data class OnProductPortionSelected(val portion: String, val selected: Boolean) : FilterScreenEvent()
}