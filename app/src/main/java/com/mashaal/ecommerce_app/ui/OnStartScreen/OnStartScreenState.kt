package com.mashaal.ecommerce_app.ui.OnStartScreen

sealed class OnStartScreenState {
    data object Loading : OnStartScreenState()
    data class Success(
        val shouldShowOnStartScreen: Boolean = true,
        val onboardingJustCompleted: Boolean = false
    ) : OnStartScreenState()
    data class Error(val message: String) : OnStartScreenState()
}

sealed class OnStartScreenEvent {
    data object OnGetStartedClicked : OnStartScreenEvent()
} 