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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.ui.Common.LoadingState
import com.mashaal.ecommerce_app.ui.Common.SearchBar
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.ProductsRow
import com.mashaal.ecommerce_app.ui.Common.SearchResultsContent
import com.mashaal.ecommerce_app.ui.theme.*

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    onProductClick: (Product) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = White,
        contentColor = White,
        ) { paddingValues ->
        if (state.isLoading) {
            LoadingState()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_nectar),
                    contentDescription = stringResource(R.string.carrot_icon),
                    modifier = Modifier
                        .size(25.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Icon(
                        imageVector = AppIcons.Location,
                        contentDescription = stringResource(R.string.location_icon),
                        modifier = Modifier
                            .size(28.dp)
                            .padding(end = 7.dp),
                        tint = LocationIconColor
                    )
                    Text(
                        text = state.location,
                        style = MaterialTheme.typography.bodyLarge,
                        color = LocationTextColor
                    )
                }
                SearchBar(
                    query = state.searchQuery,
                    onQueryChange = { viewModel.onEvent(MainScreenEvent.OnSearchQueryChange(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp
                        )
                )
                if (state.isSearchActive) {
                    SearchResultsContent(
                        isSearching = state.isSearching,
                        searchResults = state.searchResults,
                        searchQuery = state.searchQuery,
                        onProductClick = onProductClick,
                        onAddToCartClick = { product -> viewModel.onEvent(MainScreenEvent.OnAddToCartClicked(product)) },
                        modifier = Modifier.padding(top = 16.dp)
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BannerCarousel(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 8.dp
                                )
                        )
                
                HeaderRow(R.string.exclusive_offer, {
                    // Handle see all click
                })
                if (state.exclusiveOffers.isNotEmpty()) {
                    ProductsRow(
                        products = state.exclusiveOffers,
                        onProductClick = onProductClick,
                        onAddToCartClick = { product -> viewModel.onEvent(MainScreenEvent.OnAddToCartClicked(product)) }
                    )
                } else {
                    Text(
                        text = stringResource(R.string.no_exclusive_offers),
                        style = AppTextStyles.EmptyStateText,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                
                HeaderRow(R.string.best_selling, {
                    // Handle see all click
                })
                if (state.bestSelling.isNotEmpty()) {
                    ProductsRow(
                        products = state.bestSelling,
                        onProductClick = onProductClick,
                        onAddToCartClick = { product -> viewModel.onEvent(MainScreenEvent.OnAddToCartClicked(product)) }
                    )
                } else {
                    Text(
                        text = stringResource(R.string.no_best_selling),
                        style = AppTextStyles.EmptyStateText,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                
                HeaderRow(R.string.groceries, {
                    // Handle see all click
                })
                if (state.groceries.isNotEmpty()) {
                    ProductsRow(
                        products = state.groceries,
                        onProductClick = onProductClick,
                        onAddToCartClick = { product -> viewModel.onEvent(MainScreenEvent.OnAddToCartClicked(product)) }
                    )
                } else {
                    Text(
                        text = stringResource(R.string.no_grocery_products),
                        style = AppTextStyles.EmptyStateText,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                        Spacer(Modifier.height(50.dp))
                    }
                }
            }
        }
    }
}