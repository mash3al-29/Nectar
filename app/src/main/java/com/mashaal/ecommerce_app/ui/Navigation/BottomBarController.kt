package com.mashaal.ecommerce_app.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.mashaal.ecommerce_app.ui.Common.BottomNavBar

@Composable
fun BottomBarController(
    currentRoute: String?,
    navController: NavHostController
) {
    val selectedTabIndex = when (currentRoute) {
        Screen.Main.route -> 0
        Screen.Categories.route -> 1
        Screen.MyCart.route -> 2
        else -> 0
    }

    if (currentRoute == Screen.Main.route || currentRoute == Screen.Categories.route || currentRoute == Screen.MyCart.route) {
        BottomNavBar(
            onShopClick = {
                if (currentRoute != Screen.Main.route) {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Main.route) { inclusive = true }
                    }
                }
            },
            onExploreClick = {
                if (currentRoute != Screen.Categories.route) {
                    navController.navigate(Screen.Categories.route)
                }
            },
            onCartClick = {
                if (currentRoute != Screen.MyCart.route) {
                    navController.navigate(Screen.MyCart.route)
                }
            },
            selectedIndex = selectedTabIndex
        )
    }
}
