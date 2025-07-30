package com.mashaal.ecommerce_app.ui.CategorySelectedScreen

import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.ui.FilterScreen.PriceRange
import com.mashaal.ecommerce_app.ui.FilterScreen.ProductPortion

sealed class CategorySelectedState {
    data object Loading : CategorySelectedState()
    data class Success(
        val categoryName: String = "",
        val products: List<Product> = emptyList(),
        val filteredProducts: List<Product> = emptyList(),
        val selectedPriceRange: PriceRange? = null,
        val selectedProductPortions: Set<ProductPortion> = emptySet()
    ) : CategorySelectedState()
    data class Error(val message: String) : CategorySelectedState()
}

sealed class CategorySelectedEvent {
    data class OnAddToCartClicked(val product: Product) : CategorySelectedEvent()
}