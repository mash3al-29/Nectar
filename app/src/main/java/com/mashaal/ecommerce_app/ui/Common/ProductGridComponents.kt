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
    modifier: Modifier = Modifier,
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onAddToCartClick: (Product) -> Unit,
    cartProductIds: Set<Int> = emptySet(),
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = MaterialTheme.appDimensions.dimen24,
            end = MaterialTheme.appDimensions.dimen24,
            bottom = MaterialTheme.appDimensions.dimen80
        ),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.dimen16),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.dimen16),
        modifier = modifier.fillMaxSize()
    ) {
        items(products) { product ->
            ProductItem(
                product = product,
                onProductClick = onProductClick,
                onAddToCartClick = onAddToCartClick,
                isInCart = cartProductIds.contains(product.id)
            )
        }
    }
}