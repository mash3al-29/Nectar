package com.mashaal.ecommerce_app.ui.FilterScreen

import com.mashaal.ecommerce_app.R

enum class PriceRange(val stringResId: Int, val minPrice: Double, val maxPrice: Double) {
    UNDER_2(R.string.price_under_2, 0.0, 2.0),
    RANGE_2_TO_4(R.string.price_2_to_4, 2.0, 4.0),
    RANGE_4_TO_6(R.string.price_4_to_6, 4.0, 6.0),
    RANGE_6_TO_8(R.string.price_6_to_8, 6.0, 8.0),
    RANGE_8_TO_10(R.string.price_8_to_10, 8.0, 10.0),
    RANGE_10_TO_12(R.string.price_10_to_12, 10.0, 12.0),
    RANGE_12_TO_15(R.string.price_12_to_15, 12.0, 15.0),
    RANGE_15_TO_20(R.string.price_15_to_20, 15.0, 20.0)
}

enum class ProductPortion(val stringResId: Int) {
    KG_1(R.string.portion_1kg),
    L_1(R.string.portion_1L),
    G_500(R.string.portion_500g),
    G_300(R.string.portion_300g),
    G_400(R.string.portion_400g),
    PC_1(R.string.portion_1pc),
    G_150(R.string.portion_150g),
    PCS_12(R.string.portion_12pcs),
    G_250(R.string.portion_250g),
    BAGS_25(R.string.portion_25_bags),
    ML_500(R.string.portion_500ml)
}

sealed class FilterScreenState {
    data object Loading : FilterScreenState()
    data class Success(
        val categoryName: String = "",
        val availableProductPortions: List<ProductPortion> = ProductPortion.entries,
        val selectedPriceRange: PriceRange? = null,
        val selectedProductPortions: Set<ProductPortion> = emptySet()
    ) : FilterScreenState()
    
    data class Error(val message: String) : FilterScreenState()
}

sealed class FilterScreenEvent {
    data class OnPriceRangeSelected(val priceRange: PriceRange?) : FilterScreenEvent()
    data class OnProductPortionSelected(val portion: ProductPortion) : FilterScreenEvent() // Now using enum directly
}