package com.mashaal.ecommerce_app.ui.MainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.domain.model.Product

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = hiltViewModel(),
    onProductClick: (Product) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        bottomBar = {
            BottomNavBar(
                onShopClick = { viewModel.onEvent(MainScreenEvent.OnShopTabClicked) },
                onExploreClick = { viewModel.onEvent(MainScreenEvent.OnExploreTabClicked) },
                onCartClick = { viewModel.onEvent(MainScreenEvent.OnCartTabClicked) }
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
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
                    .padding(top = 50.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_nectar),
                    contentDescription = "Carrot Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location Icon",
                        modifier = modifier.padding(end = 7.dp),
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Exclusive Offer",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    SeeAllButton(
                        onClick = { // Handle see all click
                            }
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                if (state.exclusiveOffers.isNotEmpty()) {
                    ProductsRow(
                        products = state.exclusiveOffers,
                        onProductClick = onProductClick,
                        onAddToCartClick = { viewModel.onEvent(MainScreenEvent.OnAddToCartClicked) }
                    )
                } else {
                    Text("No exclusive offers available")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Best Selling",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    SeeAllButton(
                        onClick = { // Handle see all click
                            }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (state.bestSelling.isNotEmpty()) {
                    ProductsRow(
                        products = state.bestSelling,
                        onProductClick = onProductClick,
                        onAddToCartClick = { viewModel.onEvent(MainScreenEvent.OnAddToCartClicked) }
                    )
                } else {
                    Text("No best selling products available")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Groceries",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    SeeAllButton(
                        onClick = {
                        // Handle see all click
                            }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (state.groceries.isNotEmpty()) {
                    ProductsRow(
                        products = state.groceries,
                        onProductClick = onProductClick,
                        onAddToCartClick = { viewModel.onEvent(MainScreenEvent.OnAddToCartClicked) }
                    )
                } else {
                    Text("No grocery products available")
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}