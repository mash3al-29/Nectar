package com.mashaal.ecommerce_app.ui.FilterScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FilterScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(FilterScreenState())
    val state: StateFlow<FilterScreenState> = _state.asStateFlow()

    init {
        initializeFromNavArgs()
    }
    
    private fun initializeFromNavArgs() {
        savedStateHandle.get<String>("categoryName")?.let { categoryName ->
            _state.update { it.copy(categoryName = categoryName) }
        }
    }

    fun onEvent(event: FilterScreenEvent) {
        when (event) {
            is FilterScreenEvent.OnPriceRangeSelected -> {
                _state.update { it.copy(selectedPriceRange = event.priceRange, error = null, isLoading = false) }
            }
            is FilterScreenEvent.OnProductPortionSelected -> {
                val currentDetails = _state.value.selectedProductPortions.toMutableSet()
                if (event.selected) {
                    currentDetails.add(event.portion)
                } else {
                    currentDetails.remove(event.portion)
                }
                _state.update { it.copy(selectedProductPortions = currentDetails, error = null, isLoading = false) }
            }
        }
    }
}