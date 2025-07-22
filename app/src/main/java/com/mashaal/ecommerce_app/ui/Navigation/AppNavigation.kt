package com.mashaal.ecommerce_app.ui.Navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mashaal.ecommerce_app.ui.OnStartScreen.OnStartScreen
import com.mashaal.ecommerce_app.ui.SplashScreen.SplashScreen
import com.mashaal.ecommerce_app.ui.MainScreen.MainScreen
import com.mashaal.ecommerce_app.ui.ProductScreen.ProductScreen
import com.mashaal.ecommerce_app.ui.ProductScreen.ProductScreenViewModel

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object OnStart : Screen("home")
    object Main : Screen("route")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val actions = remember(navController) { NavigationActions(navController) }

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
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
                onAddToCartClick = { /* Handle add to cart */ },
                viewModel = viewModel
            )
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
}
