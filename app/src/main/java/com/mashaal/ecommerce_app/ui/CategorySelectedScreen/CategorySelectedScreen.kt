package com.mashaal.ecommerce_app.ui.CategorySelectedScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.ProductItem
import com.mashaal.ecommerce_app.ui.theme.AppIcons
import com.mashaal.ecommerce_app.ui.theme.GilroyBoldFont
import com.mashaal.ecommerce_app.ui.theme.MainThemeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectedScreen(
    categoryName: String,
    onBackClick: () -> Unit,
    onProductClick: (Product) -> Unit,
    viewModel: CategorySelectedViewModel = hiltViewModel()
) {
    LaunchedEffect(categoryName) {
        viewModel.loadProductsByCategory(categoryName)
    }
    
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showFilterBottomSheet by remember { mutableStateOf(state.showFilterBottomSheet) }
    
    LaunchedEffect(state.showFilterBottomSheet) {
        showFilterBottomSheet = state.showFilterBottomSheet
    }
    val bottomSheetState = rememberModalBottomSheetState()
    
    if (showFilterBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { 
                viewModel.onEvent(CategorySelectedEvent.OnFilterClicked)
            },
            sheetState = bottomSheetState,
            containerColor = Color.White
        ) {
            FilterBottomSheet(
                productPortions = state.productPortions,
                selectedPriceRange = state.selectedPriceRange,
                selectedProductPortions = state.selectedProductPortions,
                onPriceRangeSelected = { priceRange ->
                    viewModel.onEvent(CategorySelectedEvent.OnPriceRangeSelected(priceRange))
                },
                onProductDetailSelected = { detail, selected ->
                    viewModel.onEvent(CategorySelectedEvent.OnProductPortionSelected(detail, selected))
                },
                onApplyFilter = {
                    viewModel.onEvent(CategorySelectedEvent.OnApplyFilter)
                }
            )
        }
    }
    Scaffold(
        topBar = {
            CategoryTopBar(
                title = state.categoryName,
                onBackClick = onBackClick,
                onFilterClick = { viewModel.onEvent(CategorySelectedEvent.OnFilterClicked) }
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = Color.White,
        contentColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MainThemeColor
                )
            } else if (state.error != null) {
                Text(
                    text = state.error ?: "An error occurred",
                    color = Color.Red,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (state.products.isEmpty()) {
                        Text(
                            text = "No products found for ${state.categoryName}",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(16.dp),
                            color = Color.Gray
                        )
                    } else if (state.filteredProducts.isEmpty() && state.error != null) {
                        Text(
                            text = state.error ?: "No products found with this criteria",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(16.dp),
                            color = Color.Gray
                        )
                    } else {
                        ProductsGrid(
                            products = if (state.filteredProducts.isNotEmpty()) state.filteredProducts else state.products,
                            onProductClick = onProductClick,
                            onAddToCartClick = { viewModel.onEvent(CategorySelectedEvent.OnAddToCartClicked) }
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
                tint = Color.Black,
            )
        }
        
        Text(
            text = title,
            fontFamily = GilroyBoldFont,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        IconButton(
            onClick = onFilterClick,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                modifier = Modifier.size(35.dp),
                imageVector = AppIcons.Filter,
                contentDescription = "Filter",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun ProductsGrid(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onAddToCartClick: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 25.dp,
            end = 25.dp,
            bottom = 25.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(products) { product ->
            ProductItem(
                product = product,
                onProductClick = onProductClick,
                onAddToCartClick = onAddToCartClick
            )
        }
    }
}