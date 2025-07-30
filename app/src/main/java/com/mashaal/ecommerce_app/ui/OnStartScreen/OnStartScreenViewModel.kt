package com.mashaal.ecommerce_app.ui.OnStartScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashaal.ecommerce_app.data.util.FirstTimeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnStartScreenViewModel @Inject constructor(
    private val firstTimeManager: FirstTimeManager
) : ViewModel() {
    
    private val _state = MutableStateFlow<OnStartScreenState>(OnStartScreenState.Loading)
    val state: StateFlow<OnStartScreenState> = _state
    
    init {
        checkFirstTime()
    }
    
    fun onEvent(event: OnStartScreenEvent) {
        when (event) {
            is OnStartScreenEvent.OnGetStartedClicked -> {
                markFirstTimeCompleted()
            }
        }
    }
    
    private fun checkFirstTime() {
        viewModelScope.launch {
            try {
                val isFirstTime = firstTimeManager.isFirstTime()
                _state.value = OnStartScreenState.Success(shouldShowOnStartScreen = isFirstTime, onboardingJustCompleted = false)
            } catch (e: Exception) {
                _state.value = OnStartScreenState.Error(e.message ?: "Failed to check first time status")
            }
        }
    }
    
    private fun markFirstTimeCompleted() {
        viewModelScope.launch {
            try {
                firstTimeManager.setFirstTimeCompleted()
                _state.value = OnStartScreenState.Success(shouldShowOnStartScreen = false, onboardingJustCompleted = true)
            } catch (e: Exception) {
                _state.value = OnStartScreenState.Error(e.message ?: "Failed to complete onboarding")
            }
        }
    }
} 