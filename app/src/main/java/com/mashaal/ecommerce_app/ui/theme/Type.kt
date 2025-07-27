package com.mashaal.ecommerce_app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mashaal.ecommerce_app.R


val GilroyBoldFont = FontFamily(
    Font(R.font.gilroy_bold)
)

val GilroyMediumFont = FontFamily(
    Font(R.font.gilroy_medium)
)

val GilroyRegularFont = FontFamily(
    Font(R.font.gilroy_regular)
)
val Typography = Typography(
    titleMedium = TextStyle(
        fontFamily = GilroyBoldFont,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    bodySmall = TextStyle(
        fontFamily = GilroyMediumFont,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = GilroyRegularFont,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = GilroyRegularFont,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = GilroyRegularFont,
        fontWeight = FontWeight.W600,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    )
)