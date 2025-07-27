package com.mashaal.ecommerce_app.ui.Navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mashaal.ecommerce_app.ui.OnStartScreen.OnStartScreen
import com.mashaal.ecommerce_app.ui.SplashScreen.SplashScreen
import com.mashaal.ecommerce_app.ui.MainScreen.MainScreen
import com.mashaal.ecommerce_app.ui.ProductScreen.ProductScreen
import com.mashaal.ecommerce_app.ui.ProductScreen.ProductScreenViewModel
import com.mashaal.ecommerce_app.ui.CategoriesScreen.CategoriesScreen
import com.mashaal.ecommerce_app.ui.CategorySelectedScreen.CategorySelectedScreen
import com.mashaal.ecommerce_app.ui.FilterScreen.FilterScreen
import com.mashaal.ecommerce_app.ui.FilterScreen.FilterScreenViewModel
import com.mashaal.ecommerce_app.ui.MainScreen.MainScreenViewModel
import com.mashaal.ecommerce_app.ui.MyCartScreen.MyCartScreen
import com.mashaal.ecommerce_app.ui.theme.White

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
    object Filter : Screen("filter/{categoryName}") {
        fun createRoute(categoryName: String): String {
            return "filter/$categoryName"
        }
    }
    object MyCart : Screen("my_cart")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val actions = remember(navController) { NavigationActions(navController) }
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        contentWindowInsets = WindowInsets.statusBars,
        bottomBar = {
            BottomBarController(currentRoute, navController)
        },
        containerColor = White,
        contentColor = White
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
                    fadeIn(tween(500))
                },
                exitTransition = {
                    fadeOut(tween(500))
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
                    fadeIn(tween(500))
                },
                exitTransition = {
                    fadeOut(tween(500))
                }
            ) {
                val viewModel = hiltViewModel<MainScreenViewModel>()
                MainScreen(
                    viewModel = viewModel,
                    onProductClick = { product ->
                        actions.navigateToProductDetail(product.id)
                    }
                )
            }
            composable(
                route = Screen.Categories.route,
                enterTransition = {
                    fadeIn(tween(500))
                },
                exitTransition = {
                    fadeOut(tween(500))
                }
            ) {
                CategoriesScreen(
                    onCategoryClick = { category ->
                        actions.navigateToCategorySelected(category)
                    },
                    onProductClick = { product ->
                        actions.navigateToProductDetail(product.id)
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
                
                val priceRange = backStackEntry.savedStateHandle.get<String?>("selectedPriceRange")
                val productPortions = backStackEntry.savedStateHandle.get<Set<String>>("selectedProductPortions")
                
                val filterResults: Pair<String?, Set<String>>? = if (priceRange != null || (productPortions != null && productPortions.isNotEmpty())) {
                    Pair(priceRange, productPortions ?: emptySet<String>())
                } else {
                    null
                }
                
                if (filterResults != null) {
                    backStackEntry.savedStateHandle.remove<String>("selectedPriceRange")
                    backStackEntry.savedStateHandle.remove<Set<String>>("selectedProductPortions")
                }
                
                CategorySelectedScreen(
                    categoryName = categoryName,
                    onBackClick = { navController.popBackStack() },
                    onProductClick = { product ->
                        actions.navigateToProductDetail(product.id)
                    },
                    navigateToFilter = { category ->
                        actions.navigateToFilter(category)
                    },
                    filterResults = filterResults
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
                    viewModel = viewModel
                )
            }

            composable(
                route = Screen.Filter.route,
                arguments = listOf(
                    navArgument("categoryName") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val viewModel = hiltViewModel<FilterScreenViewModel>()
                FilterScreen(
                    onBackClick = { navController.navigateUp() },
                    onApplyFilter = { selectedPriceRange, selectedProductPortions ->
                        val previousBackStackEntry = navController.previousBackStackEntry
                        previousBackStackEntry?.savedStateHandle?.set("selectedPriceRange", selectedPriceRange)
                        previousBackStackEntry?.savedStateHandle?.set("selectedProductPortions", selectedProductPortions)
                        
                        navController.navigateUp()
                    },
                    viewModel = viewModel
                )
            }

            composable(
                route = Screen.MyCart.route,
                enterTransition = {
                    fadeIn(animationSpec = tween(durationMillis = 500))
                },
                exitTransition = {
                    fadeOut(animationSpec = tween(durationMillis = 500))
                }
            ) {
                MyCartScreen()
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

        fun navigateToFilter(categoryName: String) {
            navController.navigate(Screen.Filter.createRoute(categoryName))
        }

        fun navigateToMyCart() {
            navController.navigate(Screen.MyCart.route)
        }
    }
