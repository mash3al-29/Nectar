package com.mashaal.ecommerce_app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class AppTextStyles {
    @Composable
    fun screenTitle() = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 20.sp,
        fontWeight = FontWeight.W400,
        color = MaterialTheme.appColors.black,
        lineHeight = 18.sp,
        letterSpacing = 0.sp
    )

    @Composable
    fun sectionHeader() = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 18.sp,
        color = MaterialTheme.appColors.black
    )

    @Composable
    fun productTitle() = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 24.sp,
        color = MaterialTheme.appColors.black
    )

    @Composable
    fun productName() = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 16.sp,
        color = MaterialTheme.appColors.black
    )

    @Composable
    fun productDetail() = TextStyle(
        fontFamily = GilroyMediumFont,
        fontSize = 14.sp,
        color = MaterialTheme.appColors.gray
    )

    @Composable
    fun productDescription() = TextStyle(
        fontFamily = GilroyRegularFont,
        fontSize = 14.sp,
        color = MaterialTheme.appColors.darkGray,
        lineHeight = 24.sp
    )

    @Composable
    fun cartItemName() = TextStyle(
        fontFamily = GilroyBoldFont,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.1.sp,
        color = MaterialTheme.appColors.black
    )

    @Composable
    fun cartItemPortion() = TextStyle(
        fontFamily = GilroyMediumFont,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp,
        color = MaterialTheme.appColors.productDetailColor
    )

    @Composable
    fun price() = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 18.sp,
        color = MaterialTheme.appColors.black
    )

    @Composable
    fun priceDetail() = TextStyle(
        fontFamily = GilroyBoldFont,
        fontWeight = FontWeight.W600,
        fontSize = 18.sp,
        lineHeight = 27.sp,
        letterSpacing = 0.1.sp,
        color = MaterialTheme.appColors.black
    )

    @Composable
    fun largePrice() = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 24.sp,
        color = MaterialTheme.appColors.black
    )

    @Composable
    fun buttonText() = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 18.sp,
        color = MaterialTheme.appColors.white
    )

    @Composable
    fun smallButtonText() = TextStyle(
        fontFamily = GilroyBoldFont,
        fontWeight = FontWeight.W600,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp,
        color = MaterialTheme.appColors.white
    )

    @Composable
    fun quantityText() = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 18.sp,
        color = MaterialTheme.appColors.black
    )

    @Composable
    fun quantityButton() = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    @Composable
    fun seeAllLink() = TextStyle(
        fontFamily = GilroyRegularFont,
        fontWeight = FontWeight.W600,
        color = MaterialTheme.appColors.primary
    )

    @Composable
    fun navigationLabel() = TextStyle(
        fontFamily = GilroyMediumFont,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium
    )

    @Composable
    fun emptyStateText() = TextStyle(
        fontFamily = GilroyMediumFont,
        fontSize = 16.sp,
        color = MaterialTheme.appColors.gray
    )

    @Composable
    fun errorText() = TextStyle(
        fontFamily = GilroyMediumFont,
        fontSize = 16.sp,
        color = MaterialTheme.appColors.errorColor
    )

    @Composable
    fun placeholderText() = TextStyle(
        color = MaterialTheme.appColors.gray
    )

    @Composable
    fun welcomeTitle() = TextStyle(
        fontFamily = GilroyRegularFont,
        fontWeight = FontWeight.W600,
        fontSize = 48.sp,
        lineHeight = 50.sp,
        color = MaterialTheme.appColors.white
    )

    @Composable
    fun welcomeSubtitle() = TextStyle(
        fontFamily = GilroyMediumFont,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 15.sp,
        color = Color(0xFFB3B3B3)
    )

    @Composable
    fun welcomeButtonText() = TextStyle(
        fontFamily = GilroyMediumFont,
        fontSize = 18.sp,
        lineHeight = 18.sp,
        color = MaterialTheme.appColors.white
    )

    @Composable
    fun nutritionLabel() = TextStyle(
        fontFamily = GilroyMediumFont,
        fontSize = 14.sp,
        color = MaterialTheme.appColors.darkGray
    )

    @Composable
    fun orderAcceptedTitle() = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 28.sp,
        color = MaterialTheme.appColors.black
    )

    @Composable
    fun orderAcceptedSubtitle() = TextStyle(
        fontFamily = GilroyMediumFont,
        fontSize = 16.sp,
        color = MaterialTheme.appColors.searchTextColor
    )

    @Composable
    fun orderAcceptedPrice() = TextStyle(
        fontFamily = GilroyBoldFont,
        fontSize = 18.sp,
        color = MaterialTheme.appColors.black
    )
    
    @Composable
    fun splashScreenText() = TextStyle(
        fontFamily = GilroyMediumFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.W400,
        letterSpacing = 5.5.sp,
        lineHeight = 18.sp,
        color = Color.White
    )
}