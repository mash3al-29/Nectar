package com.mashaal.ecommerce_app.ui.SeeAllScreen

import com.mashaal.ecommerce_app.domain.model.Product

data class SeeAllState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = true,
    val error: String = "",
    val sectionType: SeeAllSectionType? = null
)

sealed class SeeAllEvent {
    data class OnAddToCartClicked(val product: Product) : SeeAllEvent()
}