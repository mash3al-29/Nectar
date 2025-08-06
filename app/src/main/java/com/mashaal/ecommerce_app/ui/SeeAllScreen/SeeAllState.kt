package com.mashaal.ecommerce_app.ui.SeeAllScreen

import com.mashaal.ecommerce_app.domain.model.Product

sealed class SeeAllState {
    data object Loading : SeeAllState()
    
    data class Success(
        val products: List<Product> = emptyList(),
        val sectionType: SeeAllSectionType? = null
    ) : SeeAllState()
    
    data class Error(val message: String) : SeeAllState()
}

sealed class SeeAllEvent {
    data class OnAddToCartClicked(val product: Product) : SeeAllEvent()
}