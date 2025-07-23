package com.mashaal.ecommerce_app.ui.CategoriesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashaal.ecommerce_app.domain.usecase.GetAllCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CategoriesState())
    val state: StateFlow<CategoriesState> = _state.asStateFlow()

    init {
        loadCategories()
    }

    fun onEvent(event: CategoriesEvent) {
        when (event) {
            is CategoriesEvent.OnSearchQueryChange -> {
                _state.update { it.copy(searchQuery = event.query) }
            }
            is CategoriesEvent.OnCategorySelected -> {
                _state.update { it.copy(selectedCategory = event.category) }
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = getAllCategoriesUseCase.execute()
                _state.update { it.copy(
                    categories = categories,
                    isLoading = false
                ) }
            } catch (e: Exception) {
                _state.update { it.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load categories"
                ) }
            }
        }
    }
}