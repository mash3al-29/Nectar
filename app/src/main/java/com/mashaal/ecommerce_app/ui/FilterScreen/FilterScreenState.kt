package com.mashaal.ecommerce_app.ui.FilterScreen

import com.mashaal.ecommerce_app.R

data class FilterScreenState(
    val categoryName: String = "",
    val productPortionsResIds: List<Int> = listOf(
        R.string.portion_1kg,
        R.string.portion_1L,
        R.string.portion_500g,
        R.string.portion_300g,
        R.string.portion_400g,
        R.string.portion_1pc,
        R.string.portion_150g,
        R.string.portion_12pcs,
        R.string.portion_250g,
        R.string.portion_25_bags,
        R.string.portion_500ml
    ),
    val selectedPriceRange: String? = null,
    val selectedProductPortions: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class FilterScreenEvent {
    data class OnPriceRangeSelected(val priceRange: String?) : FilterScreenEvent()
    data class OnProductPortionSelected(val portion: String, val selected: Boolean) : FilterScreenEvent()
}