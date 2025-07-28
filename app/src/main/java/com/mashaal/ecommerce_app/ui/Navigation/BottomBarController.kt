package com.mashaal.ecommerce_app.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.mashaal.ecommerce_app.ui.Common.BottomNavBar

@Composable
fun BottomBarController(
    currentRoute: String?,
    navController: NavHostController
) {
    val actions = remember(navController) { NavigationActionsBottomBar(navController) }
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
                    actions.navigateToHomeScreen()
                }
            },
            onExploreClick = {
                if (currentRoute != Screen.Categories.route) {
                    actions.navigateToCategoriesScreen()
                }
            },
            onCartClick = {
                if (currentRoute != Screen.MyCart.route) {
                    actions.navigateToMyCartScreen()
                }
            },
            selectedIndex = selectedTabIndex
        )
    }
}

class NavigationActionsBottomBar(private val navController: NavHostController) {
    fun navigateToHomeScreen(){
        navController.navigate(Screen.Main.route) {
            popUpTo(Screen.Main.route) { 
                inclusive = false
                saveState = true 
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToCategoriesScreen(){
        navController.navigate(Screen.Categories.route) {
            popUpTo(Screen.Main.route) { 
                saveState = true 
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToMyCartScreen(){
        navController.navigate(Screen.MyCart.route) {
            popUpTo(Screen.Main.route) { 
                saveState = true 
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
