package com.mashaal.ecommerce_app.ui.Navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mashaal.ecommerce_app.ui.OnStartScreen.OnStartScreen
import com.mashaal.ecommerce_app.ui.SplashScreen.SplashScreen
import com.mashaal.ecommerce_app.ui.MainScreen.MainScreen

enum class Screen(val route: String) {
    Splash("splash"),
    OnStart("home"),
    Main("route")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val actions = remember(navController) { NavigationActions(navController) }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ){
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
            MainScreen()
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
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }
}
