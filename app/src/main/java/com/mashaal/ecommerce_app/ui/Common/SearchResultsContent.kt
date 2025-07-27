package com.mashaal.ecommerce_app.ui.Common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.ui.theme.Gray

@Composable
fun SearchResultsContent(
    isSearching: Boolean,
    searchResults: List<Product>,
    searchQuery: String,
    onProductClick: (Product) -> Unit,
    onAddToCartClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        isSearching -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colorResource(R.color.main_theme_color))
            }
        }
        searchResults.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.no_products_found, searchQuery),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Gray
                )
            }
        }
        else -> {
            ProductsGrid(
                products = searchResults,
                onProductClick = onProductClick,
                onAddToCartClick = onAddToCartClick,
                modifier = modifier
            )
        }
    }
} 