package com.mashaal.ecommerce_app.ui.MainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.mashaal.ecommerce_app.domain.usecase.GetAllProductsUseCase
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state.asStateFlow()
    init {
        loadData()
    }
    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = event.query) }
            }
            is MainScreenEvent.OnCategorySelected -> {
                _state.update { it.copy(selectedCategory = event.category) }
            }
            is MainScreenEvent.OnAddToCartClicked -> {
                // Handle add to cart
            }
            is MainScreenEvent.OnShopTabClicked -> {
                _state.update { it.copy(selectedTabIndex = 0) }
            }
            is MainScreenEvent.OnExploreTabClicked -> {
                _state.update { it.copy(selectedTabIndex = 1) }
            }
            is MainScreenEvent.OnCartTabClicked -> {
                _state.update { it.copy(selectedTabIndex = 2) }
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
                getAllProductsUseCase.execute().catch { e ->
                    _state.update { it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error occurred"
                    )}
                }.collect { productsList ->
                    _state.update { it.copy(
                        exclusiveOffers = productsList,
                        bestSelling = productsList.shuffled(),
                        groceries = productsList.shuffled(),
                        isLoading = false
                    )}
                }

            }
        }
    }

