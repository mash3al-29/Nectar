package com.mashaal.ecommerce_app.ui.CategorySelectedScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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

@Composable
fun CategorySelectedScreen(
    categoryName: String,
    onBackClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    navigateToFilter: (String) -> Unit,
    filterResults: Pair<String?, Set<String>>? = null,
    viewModel: CategorySelectedViewModel = hiltViewModel()
) {
    LaunchedEffect(categoryName) {
        viewModel.loadProductsByCategory(categoryName)
    }
    
    LaunchedEffect(filterResults) {
        if (filterResults != null) {
            viewModel.applyFilterResults(filterResults.first, filterResults.second)
        }
    }
    
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    Scaffold(
        topBar = {
            CategoryTopBar(
                title = state.categoryName,
                onBackClick = onBackClick,
                onFilterClick = { navigateToFilter(state.categoryName) }
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.appColors.white,
        contentColor = MaterialTheme.appColors.white
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.appColors.white)
        ) {
            if (state.isLoading) {
                LoadingState()
            } else if (state.error != null) {
                ErrorState(state.error)
            } else {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (state.products.isEmpty()) {
                        EmptyState(stringResource(R.string.no_products_found_category, state.categoryName))
                    } else {
                        ProductsGrid(
                            products = state.filteredProducts.ifEmpty { state.products },
                            onProductClick = onProductClick,
                            modifier = Modifier.padding(bottom = MaterialTheme.appDimensions.paddingExtraLarge),
                            onAddToCartClick = { product -> viewModel.onEvent(CategorySelectedEvent.OnAddToCartClicked(product)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryTopBar(
    title: String,
    onBackClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.appDimensions.paddingLarge, vertical = MaterialTheme.appDimensions.paddingExtraLarge),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(MaterialTheme.appDimensions.iconSize)
        ) {
            Icon(
                modifier = Modifier.size(MaterialTheme.appDimensions.iconSizeLarge),
                imageVector = AppIcons.Back,
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.appColors.black,
            )
        }
        
        Text(
            text = title,
            style = MaterialTheme.appTextStyles.screenTitle(),
        )
        
        IconButton(
            onClick = onFilterClick,
            modifier = Modifier.size(MaterialTheme.appDimensions.iconSize)
        ) {
            Icon(
                modifier = Modifier.size(MaterialTheme.appDimensions.iconSizeLarge),
                imageVector = AppIcons.Filter,
                contentDescription = stringResource(R.string.filter),
                tint = MaterialTheme.appColors.black
            )
        }
    }
}

