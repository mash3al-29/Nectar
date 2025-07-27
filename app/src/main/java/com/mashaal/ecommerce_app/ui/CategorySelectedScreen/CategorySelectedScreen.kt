package com.mashaal.ecommerce_app.ui.CategorySelectedScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.ui.Common.EmptyState
import com.mashaal.ecommerce_app.ui.Common.ErrorState
import com.mashaal.ecommerce_app.ui.Common.LoadingState
import com.mashaal.ecommerce_app.ui.Common.ProductsGrid
import com.mashaal.ecommerce_app.ui.theme.AppIcons
import com.mashaal.ecommerce_app.ui.theme.Black
import com.mashaal.ecommerce_app.ui.theme.GilroyBoldFont
import com.mashaal.ecommerce_app.ui.theme.White

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
        containerColor = White,
        contentColor = White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(White)
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
                        EmptyState("No products found for ${state.categoryName}")
                    } else {
                        ProductsGrid(
                            products = state.filteredProducts.ifEmpty { state.products },
                            onProductClick = onProductClick,
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
            .padding(horizontal = 25.dp, vertical = 50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                modifier = Modifier.size(35.dp),
                imageVector = AppIcons.Back,
                contentDescription = "Back",
                tint = Black,
            )
        }
        
        Text(
            text = title,
            fontFamily = GilroyBoldFont,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Black
        )
        
        IconButton(
            onClick = onFilterClick,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                modifier = Modifier.size(35.dp),
                imageVector = AppIcons.Filter,
                contentDescription = "Filter",
                tint = Black
            )
        }
    }
}

