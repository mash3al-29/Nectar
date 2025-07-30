package com.mashaal.ecommerce_app.ui.FilterScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FilterScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow<FilterScreenState>(FilterScreenState.Success())
    val state: StateFlow<FilterScreenState> = _state

    init {
        initializeFromNavArgs()
    }
    
    private fun initializeFromNavArgs() {
        savedStateHandle.get<String>("categoryName")?.let { categoryName ->
            updateSuccessState { it.copy(categoryName = categoryName) }
        }
    }

    fun onEvent(event: FilterScreenEvent) {
        when (event) {
            is FilterScreenEvent.OnPriceRangeSelected -> {
                updateSuccessState { it.copy(selectedPriceRange = event.priceRange) }
            }
            is FilterScreenEvent.OnProductPortionSelected -> {
                updateSuccessState { currentState ->
                    val currentPortions = currentState.selectedProductPortions.toMutableSet()
                    if (currentPortions.contains(event.portion)) {
                        currentPortions.remove(event.portion)
                    } else {
                        currentPortions.add(event.portion)
                    }
                    currentState.copy(selectedProductPortions = currentPortions)
                }
            }
        }
    }
    
    private fun updateSuccessState(update: (FilterScreenState.Success) -> FilterScreenState.Success) {
        val currentState = _state.value
        if (currentState is FilterScreenState.Success) {
            _state.value = update(currentState)
        }
    }
}