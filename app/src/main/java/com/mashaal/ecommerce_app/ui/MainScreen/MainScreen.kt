package com.mashaal.ecommerce_app.ui.MainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.mashaal.ecommerce_app.ui.theme.AppIcons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.ui.Common.SearchBar
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.ProductsRow

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = hiltViewModel(),
    onProductClick: (Product) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        contentColor = Color.White,
        containerColor = Color.White,
        modifier = modifier
    ) { paddingValues ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    color = colorResource(R.color.main_theme_color)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_nectar),
                    contentDescription = stringResource(R.string.carrot_icon),
                    modifier = Modifier.size(70.dp).padding(top = 50.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Icon(
                        imageVector = AppIcons.Location,
                        contentDescription = stringResource(R.string.location_icon),
                        modifier = Modifier.size(28.dp).padding(end = 7.dp),
                        tint = Color.Black
                    )
                    Text(
                        text = state.location,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                SearchBar(
                    query = state.searchQuery,
                    onQueryChange = { viewModel.onEvent(MainScreenEvent.OnSearchQueryChange(it)) },
                    onSearch = { // Handle search
                         },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                BannerCarousel(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                HeaderRow(R.string.exclusive_offer, {
                    // Handle see all click
                })
                Spacer(modifier = Modifier.height(8.dp))
                if (state.exclusiveOffers.isNotEmpty()) {
                    ProductsRow(
                        products = state.exclusiveOffers,
                        onProductClick = onProductClick,
                        onAddToCartClick = { viewModel.onEvent(MainScreenEvent.OnAddToCartClicked) }
                    )
                } else {

                    Text(stringResource(R.string.no_exclusive_offers))
                }
                Spacer(modifier = Modifier.height(16.dp))
                HeaderRow(R.string.best_selling, {
                    // Handle see all click
                })
                Spacer(modifier = Modifier.height(8.dp))
                if (state.bestSelling.isNotEmpty()) {
                    ProductsRow(
                        products = state.bestSelling,
                        onProductClick = onProductClick,
                        onAddToCartClick = { viewModel.onEvent(MainScreenEvent.OnAddToCartClicked) }
                    )
                } else {
                    Text(stringResource(R.string.no_best_selling))
                }
                Spacer(modifier = Modifier.height(16.dp))
                HeaderRow(R.string.groceries, {
                // Handle see all click
                })
                Spacer(modifier = Modifier.height(8.dp))
                if (state.groceries.isNotEmpty()) {
                    ProductsRow(
                        products = state.groceries,
                        onProductClick = onProductClick,
                        onAddToCartClick = { viewModel.onEvent(MainScreenEvent.OnAddToCartClicked) }
                    )
                } else {
                    Text(stringResource(R.string.no_grocery_products))
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}