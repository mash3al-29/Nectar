package com.mashaal.ecommerce_app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTextStyles {
    val ScreenTitle = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 20.sp,
        fontWeight = FontWeight.W400,
        color = Black,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    )
    val ProductTitle = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 24.sp,
        color = Black
    )
    
    val SectionHeader = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 18.sp,
        color = Black
    )
    
    val ProductName = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 16.sp,
        color = Black
    )
    
    val ProductDetail = TextStyle(
        fontFamily = GilroyMediumFont,
        fontSize = 14.sp,
        color = Gray
    )
    
    val ProductDescription = TextStyle(
        fontFamily = GilroyRegularFont,
        fontSize = 14.sp,
        color = DarkGray,
        lineHeight = 24.sp
    )
    
    val CartItemName = TextStyle(
        fontFamily = GilroyBoldFont,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.1.sp,
        color = Black
    )
    
    val CartItemPortion = TextStyle(
        fontFamily = GilroyMediumFont,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp,
        color = ProductDetailColor
    )
    
    val Price = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 18.sp,
        color = Black
    )
    
    val PriceDetail = TextStyle(
        fontFamily = GilroyBoldFont,
        fontWeight = FontWeight.W600,
        fontSize = 18.sp,
        lineHeight = 27.sp,
        letterSpacing = 0.1.sp,
        color = Black
    )
    
    val LargePrice = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 24.sp,
        color = Black
    )
    
    val ButtonText = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 18.sp,
        color = White
    )
    
    val SmallButtonText = TextStyle(
        fontFamily = GilroyBoldFont,
        fontWeight = FontWeight.W600,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp,
        color = White
    )
    
    val QuantityText = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 18.sp,
        color = Black
    )
    
    val QuantityButton = TextStyle(
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold
    )
    
    val SeeAllLink = TextStyle(
        fontFamily = GilroyRegularFont,
        fontWeight = FontWeight.W600,
        color = MainThemeColor
    )
    
    val NavigationLabel = TextStyle(
        fontFamily = GilroyMediumFont,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium
    )
    
    val EmptyStateText = TextStyle(
        fontFamily = GilroyMediumFont,
        fontSize = 16.sp,
        color = Gray
    )
    
    val ErrorText = TextStyle(
        fontFamily = GilroyMediumFont,
        fontSize = 16.sp,
        color = ErrorColor
    )
    
    val PlaceholderText = TextStyle(
        color = Gray
    )
}

val AppTypography = Typography(
    displayLarge = AppTextStyles.ScreenTitle,
    displayMedium = AppTextStyles.ProductTitle,
    displaySmall = AppTextStyles.SectionHeader,
    headlineLarge = AppTextStyles.ProductName,
    headlineMedium = AppTextStyles.ProductDetail,
    headlineSmall = AppTextStyles.ProductDescription,
    titleLarge = AppTextStyles.Price,
    titleMedium = AppTextStyles.ButtonText,
    titleSmall = AppTextStyles.QuantityText,
    bodyLarge = AppTextStyles.EmptyStateText,
    bodyMedium = AppTextStyles.PlaceholderText,
    bodySmall = AppTextStyles.NavigationLabel,
    labelLarge = AppTextStyles.SeeAllLink,
    labelMedium = AppTextStyles.SmallButtonText,
    labelSmall = AppTextStyles.ErrorText
)