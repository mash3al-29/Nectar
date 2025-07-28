package com.mashaal.ecommerce_app.ui.SeeAllScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.ui.Common.EmptyState
import com.mashaal.ecommerce_app.ui.Common.ErrorState
import com.mashaal.ecommerce_app.ui.Common.LoadingState
import com.mashaal.ecommerce_app.ui.Common.ProductsGrid
import com.mashaal.ecommerce_app.ui.theme.AppIcons
import com.mashaal.ecommerce_app.ui.theme.appColors
import com.mashaal.ecommerce_app.ui.theme.appDimensions
import com.mashaal.ecommerce_app.ui.theme.appTextStyles

@Composable
fun SeeAllScreen(
    sectionType: SeeAllSectionType,
    onBackClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    viewModel: SeeAllViewModel = hiltViewModel()
) {
    LaunchedEffect(sectionType) {
        viewModel.loadProducts(sectionType)
    }
    
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    Scaffold(
        topBar = {
            SeeAllTopBar(
                title = stringResource(sectionType.titleResId),
                onBackClick = onBackClick
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
            } else if (state.error.isNotBlank()) {
                ErrorState(state.error)
            } else {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (state.products.isEmpty()) {
                        EmptyState(stringResource(sectionType.emptyStateResId))
                    } else {
                        ProductsGrid(
                            products = state.products,
                            onProductClick = onProductClick,
                            modifier = Modifier.padding(bottom = MaterialTheme.appDimensions.paddingSuperLarge),
                            onAddToCartClick = { product -> viewModel.onEvent(SeeAllEvent.OnAddToCartClicked(product)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SeeAllTopBar(
    title: String,
    onBackClick: () -> Unit
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
        Box(modifier = Modifier.size(MaterialTheme.appDimensions.iconSize))
    }
}