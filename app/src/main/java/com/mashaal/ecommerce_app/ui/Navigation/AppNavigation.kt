package com.mashaal.ecommerce_app.ui.Navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mashaal.ecommerce_app.ui.Common.BottomNavBar
import com.mashaal.ecommerce_app.ui.OnStartScreen.OnStartScreen
import com.mashaal.ecommerce_app.ui.SplashScreen.SplashScreen
import com.mashaal.ecommerce_app.ui.MainScreen.MainScreen
import com.mashaal.ecommerce_app.ui.ProductScreen.ProductScreen
import com.mashaal.ecommerce_app.ui.ProductScreen.ProductScreenViewModel
import com.mashaal.ecommerce_app.ui.CategoriesScreen.CategoriesScreen
import com.mashaal.ecommerce_app.ui.CategorySelectedScreen.CategorySelectedScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object OnStart : Screen("home")
    object Main : Screen("route")
    object Categories : Screen("categories")
    object CategorySelected : Screen("category_selected/{categoryName}") {
        fun createRoute(categoryName: String) = "category_selected/$categoryName"
    }
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val actions = remember(navController) { NavigationActions(navController) }
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val selectedTabIndex = when (currentRoute) {
        Screen.Main.route -> 0
        Screen.Categories.route -> 1
        else -> 0
    }
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            when (currentRoute) {
                Screen.Main.route, Screen.Categories.route -> {
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
                            // to be implemented
                        },
                        selectedIndex = selectedTabIndex
                    )
                }
                else -> {}
            }
        },
        containerColor = Color.White,
        contentColor = Color.White
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(
                route = Screen.Splash.route,
                enterTransition = {
                    fadeIn(animationSpec = tween(durationMillis = 1000, delayMillis = 2000))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(durationMillis = 1000, delayMillis = 2000))
                }
            ) {
                SplashScreen(
                    onNavigateToHomeFromSplash = {
                        actions.navigateToHomeFromSplash()
                    }
                )
            }
            composable(
                route = Screen.OnStart.route,
                enterTransition = {
                    fadeIn(animationSpec = tween(durationMillis = 1000))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(durationMillis = 1000))
                }
            ) {
                OnStartScreen(
                    onNavigateToMainScreen = {
                        actions.navigateToMainScreen()
                    }
                )
            }
            composable(
                route = Screen.Main.route,
                enterTransition = {
                    fadeIn(animationSpec = tween(durationMillis = 1000))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(durationMillis = 1000))
                }
            ) {
                MainScreen(
                    onProductClick = { product ->
                        actions.navigateToProductDetail(product.id)
                    }
                )
            }
            composable(
                route = Screen.Categories.route,
                enterTransition = {
                    fadeIn(animationSpec = tween(durationMillis = 1000))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(durationMillis = 1000))
                }
            ) {
                CategoriesScreen(
                    onCategoryClick = { category ->
                        actions.navigateToCategorySelected(category)
                    }
                )
            }
            composable(
                route = Screen.CategorySelected.route,
                arguments = listOf(
                    navArgument("categoryName") {
                        type = NavType.StringType
                    }
                ),
                enterTransition = {
                    fadeIn(animationSpec = tween(durationMillis = 500))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(durationMillis = 500))
                }
            ) { backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                CategorySelectedScreen(
                    categoryName = categoryName,
                    onBackClick = { navController.popBackStack() },
                    onProductClick = { product ->
                        actions.navigateToProductDetail(product.id)
                    },
                )
            }
            composable(
                route = Screen.ProductDetail.route,
                arguments = listOf(
                    navArgument("productId") {
                        type = NavType.IntType
                    }
                ),
                enterTransition = {
                    fadeIn(animationSpec = tween(durationMillis = 500))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(durationMillis = 500))
                }
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt("productId") ?: 0
                val viewModel = hiltViewModel<ProductScreenViewModel>()
                LaunchedEffect(productId) {
                    viewModel.loadProductById(productId)
                }
                ProductScreen(
                    onBackClick = { navController.popBackStack() },
                    onAddToCartClick = {
                    // to be implemented
                    },
                    viewModel = viewModel
                )
            }
        }
    }
}
    class NavigationActions(private val navController: NavHostController) {
        fun navigateToHomeFromSplash() {
            navController.navigate(Screen.OnStart.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }

        fun navigateToMainScreen() {
            navController.navigate(Screen.Main.route) {
                popUpTo(Screen.OnStart.route) { inclusive = true }
            }
        }

        fun navigateToProductDetail(productId: Int) {
            navController.navigate(Screen.ProductDetail.createRoute(productId))
        }
        
        fun navigateToCategorySelected(categoryName: String) {
            navController.navigate(Screen.CategorySelected.createRoute(categoryName))
        }
    }
