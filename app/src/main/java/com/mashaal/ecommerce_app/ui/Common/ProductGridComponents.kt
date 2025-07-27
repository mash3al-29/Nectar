package com.mashaal.ecommerce_app.ui.Common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.ProductItem

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
            start = 25.dp,
            end = 25.dp,
            bottom = 25.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
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