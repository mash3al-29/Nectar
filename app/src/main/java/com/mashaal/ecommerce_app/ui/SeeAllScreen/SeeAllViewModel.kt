package com.mashaal.ecommerce_app.ui.SeeAllScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashaal.ecommerce_app.domain.extensions.groceries
import com.mashaal.ecommerce_app.domain.extensions.isBestSelling
import com.mashaal.ecommerce_app.domain.extensions.isExclusiveOffer
import com.mashaal.ecommerce_app.domain.model.Cart
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.domain.usecase.AddToCartUseCase
import com.mashaal.ecommerce_app.domain.usecase.GetAllProductsUseCase
import com.mashaal.ecommerce_app.domain.usecase.GetCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllViewModel @Inject constructor(
    getAllProductsUseCase: GetAllProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    getCartUseCase: GetCartUseCase
) : ViewModel() {
    
    private val _sectionType = MutableStateFlow<SeeAllSectionType?>(null)

    val cartProductIds: StateFlow<Set<Int>> = getCartUseCase()
        .catch { emit(Cart(emptyList())) }
        .map { cart -> cart.items.map { it.product.id }.toSet() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    private val productsFlow = combine(
        getAllProductsUseCase().catch { emit(emptyList()) },
        _sectionType
    ) { allProducts, sectionType ->
        when (sectionType) {
            SeeAllSectionType.EXCLUSIVE_OFFERS -> allProducts.filter { it.isExclusiveOffer }
            SeeAllSectionType.BEST_SELLING -> allProducts.filter { it.isBestSelling }
            SeeAllSectionType.GROCERIES -> allProducts.filter { it.groceries }
            null -> emptyList()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val state: StateFlow<SeeAllState> = productsFlow
        .map { products ->
            if (_sectionType.value == null) {
                SeeAllState.Loading
            } else {
                SeeAllState.Success(products = products)
            }
        }
        .catch { throwable ->
            emit(SeeAllState.Error(throwable.message ?: "Failed to load products"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SeeAllState.Loading
        )

    fun onEvent(event: SeeAllEvent) {
        when (event) {
            is SeeAllEvent.OnAddToCartClicked -> {
                addToCart(event.product)
            }
        }
    }

    private fun addToCart(product: Product) {
        viewModelScope.launch {
            try {
                addToCartUseCase(
                    productId = product.id,
                    quantity = 1,
                    portion = product.detail
                )
            } catch (e: Exception) {
                println("Failed to add product to cart: ${e.message}")
            }
        }
    }

    fun loadProductsBySection(sectionType: SeeAllSectionType) {
        _sectionType.value = sectionType
    }
}