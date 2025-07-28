package com.mashaal.ecommerce_app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppColors = staticCompositionLocalOf { AppColors() }
val LocalAppDimensions = staticCompositionLocalOf { AppDimensions() }
val LocalAppShapes = staticCompositionLocalOf { AppShapes() }

val LocalAppTextStyles = staticCompositionLocalOf { AppTextStyles() }

val MaterialTheme.appColors: AppColors
    @Composable get() = LocalAppColors.current

val MaterialTheme.appDimensions: AppDimensions
    @Composable get() = LocalAppDimensions.current

val MaterialTheme.appShapes: AppShapes
    @Composable get() = LocalAppShapes.current

val MaterialTheme.appTextStyles: AppTextStyles
    @Composable get() = LocalAppTextStyles.current
