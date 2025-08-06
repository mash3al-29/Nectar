package com.mashaal.ecommerce_app.ui.CategorySelectedScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.ui.Common.EmptyState
import com.mashaal.ecommerce_app.ui.Common.ErrorState
import com.mashaal.ecommerce_app.ui.Common.LoadingState
import com.mashaal.ecommerce_app.ui.Common.ProductsGrid
import com.mashaal.ecommerce_app.ui.theme.AppIcons
import com.mashaal.ecommerce_app.ui.theme.appColors
import com.mashaal.ecommerce_app.ui.theme.appDimensions
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.theme.appTextStyles
import com.mashaal.ecommerce_app.ui.FilterScreen.PriceRange
import com.mashaal.ecommerce_app.ui.FilterScreen.ProductPortion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectedScreen(
    categoryName: String,
    onBackClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    navigateToFilter: (String) -> Unit,
    filterResults: Pair<PriceRange?, Set<ProductPortion>>?,
    viewModel: CategorySelectedViewModel = hiltViewModel()
) {
    val currentState by viewModel.state.collectAsStateWithLifecycle()
    val cartProductIds by viewModel.cartProductIds.collectAsStateWithLifecycle()

    LaunchedEffect(categoryName) {
        viewModel.loadProductsByCategory(categoryName)
    }

    LaunchedEffect(filterResults) {
        filterResults?.let { (priceRange, productPortions) ->
            viewModel.applyFilterResults(priceRange, productPortions)
        }
    }

    when (val state = currentState) {
        is CategorySelectedState.Loading -> {
            LoadingState()
        }
        is CategorySelectedState.Error -> {
            ErrorState(error = state.message)
        }
        is CategorySelectedState.Success -> {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = categoryName,
                                style = MaterialTheme.appTextStyles.screenTitle()
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    AppIcons.Back,
                                    contentDescription = stringResource(R.string.back),
                                    Modifier.size(MaterialTheme.appDimensions.dimen28)
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { navigateToFilter(categoryName) }) {
                                Icon(AppIcons.Filter, contentDescription = stringResource(R.string.filter))
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.appColors.white,
                        ),
                        windowInsets = WindowInsets(0)
                    )
                },
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                containerColor = MaterialTheme.appColors.white,
                contentColor = MaterialTheme.appColors.black
            ) { paddingValues ->
                val productsToShow = state.filteredProducts.ifEmpty {
                    state.products
                }
                
                // check if the filters are applied but no results found
                val filtersApplied = state.selectedPriceRange != null || state.selectedProductPortions.isNotEmpty()
                val noResultsFound = filtersApplied && productsToShow.isEmpty()
                
                if (noResultsFound) {
                    EmptyState(
                        message = stringResource(R.string.no_products_found_for_filters),
                        modifier = Modifier.padding(paddingValues),
                        textStyle = MaterialTheme.appTextStyles.emptyStateText().copy(
                            color = MaterialTheme.appColors.errorColor
                        )
                    )
                } else {
                    ProductsGrid(
                        products = productsToShow,
                        onProductClick = onProductClick,
                        onAddToCartClick = { product ->
                            viewModel.onEvent(CategorySelectedEvent.OnAddToCartClicked(product))
                        },
                        cartProductIds = cartProductIds,
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}

