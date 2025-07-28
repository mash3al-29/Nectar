package com.mashaal.ecommerce_app.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp

@Immutable
data class AppShapes(
    val small: RoundedCornerShape = RoundedCornerShape(4.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(8.dp),
    val large: RoundedCornerShape = RoundedCornerShape(16.dp),
    val button: RoundedCornerShape = RoundedCornerShape(19.dp),
    val card: RoundedCornerShape = RoundedCornerShape(18.dp),
    val cardSmall: RoundedCornerShape = RoundedCornerShape(8.dp),
    val productItem: RoundedCornerShape = RoundedCornerShape(18.dp),
    val searchBar: RoundedCornerShape = RoundedCornerShape(15.dp),
    val bottomNav: RoundedCornerShape = RoundedCornerShape(
        topStart = 20.dp,
        topEnd = 20.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp),
    val quantityButton: RoundedCornerShape = RoundedCornerShape(12.dp),
    val quantityButtonText: RoundedCornerShape = RoundedCornerShape(17.dp),
    val checkbox: RoundedCornerShape = RoundedCornerShape(8.dp),
    val welcomeButton: RoundedCornerShape = RoundedCornerShape(20.dp),
    val iconButton: RoundedCornerShape = RoundedCornerShape(17.dp),
    val productImageContainer: RoundedCornerShape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp,
        bottomStart = 25.dp,
        bottomEnd = 25.dp
    )

)
