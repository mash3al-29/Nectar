package com.mashaal.ecommerce_app.ui.MainScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.theme.GilroyMediumFont
import com.mashaal.ecommerce_app.ui.theme.MainThemeColor

enum class BottomNavItem(val title: String, val icon: Int) {
    SHOP("Shop", R.drawable.ic_shop),
    EXPLORE("Explore", R.drawable.ic_explore),
    CART("Cart", R.drawable.ic_cart)
}

@Composable
fun BottomNavBar(
    onShopClick: () -> Unit,
    onExploreClick: () -> Unit,
    onCartClick: () -> Unit
) {
    val items = listOf(
        BottomNavItem.SHOP,
        BottomNavItem.EXPLORE,
        BottomNavItem.CART
    )
    var selectedItem by remember { mutableIntStateOf(0) }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        color = Color.White,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontFamily = GilroyMediumFont,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        when (index) {
                            0 -> onShopClick()
                            1 -> onExploreClick()
                            2 -> onCartClick()
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MainThemeColor,
                        selectedTextColor = MainThemeColor,
                        indicatorColor = Color.White,
                        unselectedIconColor = Color.Black,
                        unselectedTextColor = Color.Black
                    )
                )
            }
        }
    }
}
