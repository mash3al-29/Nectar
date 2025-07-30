package com.mashaal.ecommerce_app.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class AppDimensions(
    val dimen1: Dp = 1.dp,           // divider thickness, border width
    val dimen2: Dp = 2.dp,           // small elevation, medium border width
    val dimen4: Dp = 4.dp,           // extra small padding/spacing
    val dimen8: Dp = 8.dp,           // small padding/spacing, corner radius
    val dimen10: Dp = 10.dp,         // button content padding
    val dimen12: Dp = 12.dp,         // small-medium spacing, quantity button radius
    val dimen14: Dp = 14.dp,         // small-between padding
    val dimen15: Dp = 15.dp,         // search bar, bottom nav corner radius
    val dimen16: Dp = 16.dp,         // medium padding/spacing (most common)
    val dimen17: Dp = 17.dp,         // quantity button text, icon button radius
    val dimen18: Dp = 18.dp,         // card, product item corner radius
    val dimen19: Dp = 19.dp,         // button corner radius
    val dimen20: Dp = 20.dp,         // medium-large padding, small icon size, welcome button radius
    val dimen24: Dp = 24.dp,         // large padding/spacing, standard icon size
    val dimen25: Dp = 25.dp,         // bottom nav bottom corners
    val dimen28: Dp = 28.dp,         // large icon size
    val dimen32: Dp = 32.dp,         // extra large padding/spacing
    val dimen40: Dp = 40.dp,         // quantity button size
    val dimen44: Dp = 44.dp,         // category text height
    val dimen45: Dp = 45.dp,         // super large padding, icon button size
    val dimen48: Dp = 48.dp,         // maximum spacing, large icon button size
    val dimen50: Dp = 50.dp,         // search bar height, nectar height, quantity text size
    val dimen60: Dp = 60.dp,         // button height, cart product image, carrot size
    val dimen75: Dp = 75.dp,         // category image height
    val dimen80: Dp = 80.dp,         // product image size
    val dimen90: Dp = 90.dp,         // welcome button height
    val dimen93: Dp = 93.dp,         // category text width
    val dimen100: Dp = 100.dp,       // logo size, product image height
    val dimen111: Dp = 111.dp,       // category image width
    val dimen160: Dp = 160.dp,       // banner height
    val dimen170: Dp = 170.dp,       // category card height
    val dimen175: Dp = 175.dp,       // product card width
    val dimen200: Dp = 200.dp,       // nectar width, product screen image size
    val dimen220: Dp = 220.dp,       // category card width
    val dimen250: Dp = 250.dp,       // product card height
    val dimen300: Dp = 300.dp,       // product image height large, welcome button width
    val dimen350: Dp = 350.dp,       // accepted order image size
    val blurEffectRadius: Float = 30f, // used in accepted order screen for blur effect
)
