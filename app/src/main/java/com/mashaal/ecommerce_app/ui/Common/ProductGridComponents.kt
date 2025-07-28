package com.mashaal.ecommerce_app.ui.Common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.ProductItem
import com.mashaal.ecommerce_app.ui.theme.appDimensions

@Composable
fun ProductsGrid(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onAddToCartClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = MaterialTheme.appDimensions.paddingLarge,
            end = MaterialTheme.appDimensions.paddingLarge,
            bottom = MaterialTheme.appDimensions.paddingLarge
        ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.spacingMedium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.spacingMedium),
        modifier = modifier.fillMaxSize()
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