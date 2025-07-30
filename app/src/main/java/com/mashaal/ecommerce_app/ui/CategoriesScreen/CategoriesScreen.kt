package com.mashaal.ecommerce_app.ui.CategoriesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.Common.SearchBar
import com.mashaal.ecommerce_app.ui.Common.CategoriesComponents.CategoryItem
import com.mashaal.ecommerce_app.ui.Common.CategoriesComponents.getCategoryColors
import com.mashaal.ecommerce_app.ui.Common.SearchResultsContent
import com.mashaal.ecommerce_app.ui.Common.LoadingState
import com.mashaal.ecommerce_app.ui.Common.ErrorState
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.ui.theme.appColors
import com.mashaal.ecommerce_app.ui.theme.appDimensions
import com.mashaal.ecommerce_app.ui.theme.appTextStyles

@Composable
fun CategoriesScreen(
    onCategoryClick: (String) -> Unit,
    onProductClick: (Product) -> Unit = {},
    viewModel: CategoriesViewModel = hiltViewModel()
) {
    val currentState by viewModel.state.collectAsStateWithLifecycle()
    val cartProductIds by viewModel.cartProductIds.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.appColors.white,
        contentColor = MaterialTheme.appColors.white,
    ) { paddingValues ->
        when (val state = currentState) {
            is CategoriesState.Loading -> {
                LoadingState()
            }
            is CategoriesState.Error -> {
                ErrorState(error = state.message)
            }
            is CategoriesState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(top = MaterialTheme.appDimensions.dimen32)
                        .background(MaterialTheme.appColors.white)
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            })
                        }
                ) {
                    Text(
                        text = stringResource(R.string.find_products),
                        style = MaterialTheme.appTextStyles.screenTitle(),
                        modifier = Modifier
                            .padding(horizontal = MaterialTheme.appDimensions.dimen32)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen24))
                    SearchBar(
                        query = state.searchQuery,
                        onQueryChange = { viewModel.onEvent(CategoriesEvent.OnSearchQueryChange(it)) },
                        modifier = Modifier.padding(
                            start = MaterialTheme.appDimensions.dimen32, 
                            end = MaterialTheme.appDimensions.dimen32, 
                            bottom = MaterialTheme.appDimensions.dimen24
                        ),
                        focusManager = focusManager,
                        keyboardController = keyboardController!!
                    )
                    
                    if (state.searchQuery.isNotBlank()) {
                        SearchResultsContent(
                            isSearching = false,
                            searchResults = state.searchResults,
                            searchQuery = state.searchQuery,
                            onProductClick = onProductClick,
                            onAddToCartClick = { product -> 
                                viewModel.onEvent(CategoriesEvent.OnAddToCartClicked(product)) 
                            },
                            cartProductIds = cartProductIds,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(
                                start = MaterialTheme.appDimensions.dimen32,
                                end = MaterialTheme.appDimensions.dimen32,
                                bottom = MaterialTheme.appDimensions.dimen32
                            ),
                            verticalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.dimen24),
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.dimen16),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(state.categories.size) { index ->
                                val category = state.categories[index]
                                val colors = getCategoryColors(category)
                                CategoryItem(
                                    category = category,
                                    backgroundColor = colors.first,
                                    borderColor = colors.second,
                                    onClick = { onCategoryClick(category) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
