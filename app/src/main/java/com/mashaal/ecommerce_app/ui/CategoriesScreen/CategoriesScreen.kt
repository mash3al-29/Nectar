package com.mashaal.ecommerce_app.ui.CategoriesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import com.mashaal.ecommerce_app.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchQuery = state.searchQuery
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.appColors.white,
        contentColor = MaterialTheme.appColors.white,

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = MaterialTheme.appDimensions.spacingExtraLarge)
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
                    .padding(horizontal = MaterialTheme.appDimensions.paddingExtraLarge)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.spacingLarge))
            SearchBar(
                query = searchQuery,
                onQueryChange = { viewModel.onEvent(CategoriesEvent.OnSearchQueryChange(it)) },
                modifier = Modifier.padding(start = MaterialTheme.appDimensions.paddingExtraLarge, end = MaterialTheme.appDimensions.paddingExtraLarge, bottom = MaterialTheme.appDimensions.paddingLarge),
                focusManager = focusManager,
                keyboardController = keyboardController!!
            )
            if (state.isLoading) {
                LoadingState()
            } else if (state.error != null) {
                ErrorState(error = state.error)
            } else if (state.isSearchActive) {
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
                        start = MaterialTheme.appDimensions.paddingExtraLarge,
                        end = MaterialTheme.appDimensions.paddingExtraLarge,
                        bottom = MaterialTheme.appDimensions.paddingExtraLarge,
                        top = MaterialTheme.appDimensions.paddingLarge
                    ),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.spacingMedium),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.spacingMedium),
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
