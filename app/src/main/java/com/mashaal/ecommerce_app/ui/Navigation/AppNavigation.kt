package com.mashaal.ecommerce_app.ui.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
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
import com.mashaal.ecommerce_app.ui.AcceptedOrderScreen.AcceptedOrderScreen
import com.mashaal.ecommerce_app.ui.OnStartScreen.OnStartScreen
import com.mashaal.ecommerce_app.ui.OnStartScreen.OnStartScreenViewModel
import com.mashaal.ecommerce_app.ui.SplashScreen.SplashScreen
import com.mashaal.ecommerce_app.ui.MainScreen.MainScreen
import com.mashaal.ecommerce_app.ui.ProductScreen.ProductScreen
import com.mashaal.ecommerce_app.ui.ProductScreen.ProductScreenViewModel
import com.mashaal.ecommerce_app.ui.CategoriesScreen.CategoriesScreen
import com.mashaal.ecommerce_app.ui.CategorySelectedScreen.CategorySelectedScreen
import com.mashaal.ecommerce_app.ui.CategorySelectedScreen.CategorySelectedViewModel
import com.mashaal.ecommerce_app.ui.CategorySelectedScreen.CategorySelectedState
import com.mashaal.ecommerce_app.ui.FilterScreen.FilterScreen
import com.mashaal.ecommerce_app.ui.FilterScreen.FilterScreenViewModel
import com.mashaal.ecommerce_app.ui.FilterScreen.FilterScreenEvent
import com.mashaal.ecommerce_app.ui.FilterScreen.PriceRange
import com.mashaal.ecommerce_app.ui.FilterScreen.ProductPortion
import com.mashaal.ecommerce_app.ui.MainScreen.MainScreenViewModel
import com.mashaal.ecommerce_app.ui.MyCartScreen.MyCartScreen
import com.mashaal.ecommerce_app.ui.MyCartScreen.MyCartScreenViewModel
import com.mashaal.ecommerce_app.ui.SeeAllScreen.SeeAllScreen
import com.mashaal.ecommerce_app.ui.SeeAllScreen.SeeAllSectionType
import com.mashaal.ecommerce_app.ui.SeeAllScreen.SeeAllViewModel
import com.mashaal.ecommerce_app.ui.theme.appColors

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

    object AcceptedOrder : Screen("accepted_order/{totalPrice}") {
        fun createRoute(totalPrice: Double) = "accepted_order/$totalPrice"
    }
    
    object SeeAll : Screen("see_all/{sectionType}") {
        fun createRoute(sectionType: String) = "see_all/$sectionType"
    }
}

@RequiresApi(Build.VERSION_CODES.S)
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
        containerColor = MaterialTheme.appColors.white,
        contentColor = MaterialTheme.appColors.white
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
                    },
                    onNavigateToMainFromSplash = {
                        actions.navigateToMainFromSplash()
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
                val viewModel = hiltViewModel<OnStartScreenViewModel>()
                OnStartScreen(
                    onNavigateToMainScreen = {
                        actions.navigateToMainScreen()
                    },
                    viewModel = viewModel
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
                    },
                    onSeeAllClick = { sectionType ->
                        actions.navigateToSeeAll(sectionType)
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
                val priceRange = backStackEntry.savedStateHandle.get<PriceRange?>("selectedPriceRange")
                val productPortions = backStackEntry.savedStateHandle.get<Set<ProductPortion>>("selectedProductPortions")
                val filterResults: Pair<PriceRange?, Set<ProductPortion>>? =
                    if (priceRange != null || (productPortions != null && productPortions.isNotEmpty())) {
                        Pair(priceRange, productPortions ?: emptySet())
                    } else {
                        null
                    }
                LaunchedEffect(filterResults) {
                    if (filterResults != null) {
                        backStackEntry.savedStateHandle.remove<PriceRange>("selectedPriceRange")
                        backStackEntry.savedStateHandle.remove<Set<ProductPortion>>("selectedProductPortions")
                    }
                }
                val viewModel = hiltViewModel<CategorySelectedViewModel>()
                CategorySelectedScreen(
                    categoryName = categoryName,
                    onBackClick = { navController.popBackStack() },
                    onProductClick = { product ->
                        actions.navigateToProductDetail(product.id)
                    },
                    navigateToFilter = { category ->
                        val currentState = (viewModel.state.value as? CategorySelectedState.Success)
                        actions.navigateToFilter(
                            category, 
                            currentState?.selectedPriceRange,
                            currentState?.selectedProductPortions ?: emptySet()
                        )
                    },
                    filterResults = filterResults,
                    viewModel = viewModel
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
                val currentPriceRangeOrdinal = backStackEntry.savedStateHandle.get<Int?>("currentPriceRangeOrdinal")
                val currentProductPortions = backStackEntry.savedStateHandle.get<Set<ProductPortion>>("currentProductPortions") ?: emptySet()
                
                LaunchedEffect(currentPriceRangeOrdinal, currentProductPortions) {
                    currentPriceRangeOrdinal?.let { ordinal ->
                        val priceRange = PriceRange.entries.getOrNull(ordinal)
                        viewModel.onEvent(FilterScreenEvent.OnPriceRangeSelected(priceRange))
                    }
                    currentProductPortions.forEach { portion ->
                        viewModel.onEvent(FilterScreenEvent.OnProductPortionSelected(portion))
                    }
                }
                
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
                val viewModel = hiltViewModel<MyCartScreenViewModel>()
                MyCartScreen(
                    viewModel,
                ) { price ->
                    actions.navigateToOnAcceptedScreen(price)
                }
            }
            composable(
                route = Screen.AcceptedOrder.route,
                arguments = listOf(
                    navArgument("totalPrice") { type = NavType.StringType }
                ),
                enterTransition = { fadeIn(animationSpec = tween(500)) },
                exitTransition = { fadeOut(animationSpec = tween(500)) }
            ) { backStackEntry ->
                val totalPriceArg = backStackEntry.arguments?.getString("totalPrice")?.toDoubleOrNull() ?: 0.0
                AcceptedOrderScreen(
                    totalPrice = totalPriceArg,
                    onBackToHome = { actions.navigateToMainScreen() }
                )
            }
            
            composable(
                route = Screen.SeeAll.route,
                arguments = listOf(
                    navArgument("sectionType") { type = NavType.IntType }
                ),
                enterTransition = { fadeIn(animationSpec = tween(500)) },
                exitTransition = { fadeOut(animationSpec = tween(500)) }
            ) { backStackEntry ->
                val sectionTypeOrdinal = backStackEntry.arguments?.getInt("sectionType") ?: 0
                val sectionType = SeeAllSectionType.entries.toTypedArray().getOrElse(sectionTypeOrdinal) { SeeAllSectionType.EXCLUSIVE_OFFERS }
                val viewModel = hiltViewModel<SeeAllViewModel>()
                SeeAllScreen(
                    sectionType = sectionType,
                    onBackClick = { navController.popBackStack() },
                    onProductClick = { product ->
                        actions.navigateToProductDetail(product.id)
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
        fun navigateToMainFromSplash() {
            navController.navigate(Screen.Main.route) {
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
        fun navigateToFilter(categoryName: String, currentPriceRange: PriceRange? = null, currentProductPortions: Set<ProductPortion> = emptySet()) {
            navController.navigate(Screen.Filter.createRoute(categoryName))
            navController.currentBackStackEntry?.savedStateHandle?.set("currentPriceRangeOrdinal", currentPriceRange?.ordinal)
            navController.currentBackStackEntry?.savedStateHandle?.set("currentProductPortions", currentProductPortions)
        }
        fun navigateToOnAcceptedScreen(totalPrice: Double) {
            navController.navigate(Screen.AcceptedOrder.createRoute(totalPrice)) {
                popUpTo(0) { inclusive = true }
            }
        }
        fun navigateToSeeAll(sectionType: SeeAllSectionType) {
            navController.navigate(Screen.SeeAll.createRoute(sectionType.ordinal.toString()))
        }
}
