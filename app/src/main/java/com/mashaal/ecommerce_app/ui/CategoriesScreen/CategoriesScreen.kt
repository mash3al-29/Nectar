package com.mashaal.ecommerce_app.ui.CategoriesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mashaal.ecommerce_app.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mashaal.ecommerce_app.ui.Common.SearchBar
import com.mashaal.ecommerce_app.ui.Common.CategoriesComponents.CategoryItem
import com.mashaal.ecommerce_app.ui.Common.CategoriesComponents.getCategoryColors
import com.mashaal.ecommerce_app.ui.Common.SearchResultsContent
import com.mashaal.ecommerce_app.ui.theme.GilroyBoldFont
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.ui.theme.Black
import com.mashaal.ecommerce_app.ui.theme.White

@Composable
fun CategoriesScreen(
    onCategoryClick: (String) -> Unit,
    onProductClick: (Product) -> Unit = {},
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchQuery = state.searchQuery
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = White,
        contentColor = White,

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 50.dp)
                .background(White)
        ) {
            Text(
                text = stringResource(R.string.find_products),
                fontFamily = GilroyBoldFont,
                fontSize = 20.sp,
                color = Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(30.dp))
            SearchBar(
                query = searchQuery,
                onQueryChange = { viewModel.onEvent(CategoriesEvent.OnSearchQueryChange(it)) },
                modifier = Modifier.padding(horizontal = 25.dp)
            )
            if (state.isSearchActive) {
                SearchResultsContent(
                    isSearching = state.isSearching,
                    searchResults = state.searchResults,
                    searchQuery = state.searchQuery,
                    onProductClick = onProductClick,
                    onAddToCartClick = { product -> viewModel.onEvent(CategoriesEvent.OnAddToCartClicked(product)) }
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        start = 25.dp,
                        end = 25.dp,
                        bottom = 25.dp,
                        top = 20.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.categories) { category ->
                        val (backgroundColor, borderColor) = getCategoryColors(category)
                        CategoryItem(
                            category = category,
                            backgroundColor = backgroundColor,
                            borderColor = borderColor,
                            onClick = { onCategoryClick(category) }
                        )
                    }
                }
            }
        }
    }
}
