package com.mashaal.ecommerce_app.ui.MainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.ui.Common.ErrorState
import com.mashaal.ecommerce_app.ui.Common.LoadingState
import com.mashaal.ecommerce_app.ui.Common.SearchBar
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.ProductsRow
import com.mashaal.ecommerce_app.ui.Common.SearchResultsContent
import com.mashaal.ecommerce_app.ui.theme.AppIcons
import com.mashaal.ecommerce_app.ui.theme.appColors
import com.mashaal.ecommerce_app.ui.theme.appDimensions
import com.mashaal.ecommerce_app.ui.SeeAllScreen.SeeAllSectionType
import com.mashaal.ecommerce_app.ui.theme.appTextStyles

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = hiltViewModel(),
    onProductClick: (Product) -> Unit = {},
    onSeeAllClick: (SeeAllSectionType) -> Unit = {}
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
            is MainScreenState.Loading -> {
                LoadingState()
            }
            is MainScreenState.Error -> {
                ErrorState(error = state.message)
            }
            is MainScreenState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            })
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen32))
                    Image(
                        painter = painterResource(id = R.drawable.ic_nectar),
                        contentDescription = stringResource(R.string.carrot_icon),
                        modifier = Modifier
                            .size(MaterialTheme.appDimensions.dimen24)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = MaterialTheme.appDimensions.dimen24,
                                end = MaterialTheme.appDimensions.dimen24,
                                top = MaterialTheme.appDimensions.dimen8
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
                        Icon(
                            imageVector = AppIcons.Location,
                            contentDescription = stringResource(R.string.location_icon),
                            modifier = Modifier
                                .size(MaterialTheme.appDimensions.dimen28)
                                .padding(end = MaterialTheme.appDimensions.dimen4),
                            tint = MaterialTheme.appColors.locationIconColor
                        )
                        Text(
                            text = stringResource(state.locationResId),
                            style = MaterialTheme.appTextStyles.productDetail(),
                            color = MaterialTheme.appColors.locationTextColor
                        )
                    }
                    SearchBar(
                        query = state.searchQuery,
                        onQueryChange = { viewModel.onEvent(MainScreenEvent.OnSearchQueryChange(it)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = MaterialTheme.appDimensions.dimen24,
                                end = MaterialTheme.appDimensions.dimen24,
                                top = MaterialTheme.appDimensions.dimen24
                            ),
                        focusManager = focusManager,
                        keyboardController = keyboardController!!
                    )
                    
                    if (state.isSearchActive) {
                        SearchResultsContent(
                            isSearching = state.isSearching,
                            searchResults = state.searchResults,
                            searchQuery = state.searchQuery,
                            onProductClick = onProductClick,
                            onAddToCartClick = { product -> viewModel.onEvent(MainScreenEvent.OnAddToCartClicked(product)) },
                            cartProductIds = cartProductIds,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = MaterialTheme.appDimensions.dimen24)
                        )
                    } else {
                        val verticalScrollState = rememberScrollState()
                        
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = MaterialTheme.appDimensions.dimen24)
                                .verticalScroll(verticalScrollState),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            BannerCarousel(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = MaterialTheme.appDimensions.dimen24,
                                        vertical = MaterialTheme.appDimensions.dimen8
                                    )
                            )
                    
                            HeaderRow(R.string.exclusive_offer) {
                                onSeeAllClick(SeeAllSectionType.EXCLUSIVE_OFFERS)
                            }
                            if (state.exclusiveOffers.isNotEmpty()) {
                                ProductsRow(
                                    products = state.exclusiveOffers,
                                    onProductClick = onProductClick,
                                    onAddToCartClick = { product -> viewModel.onEvent(MainScreenEvent.OnAddToCartClicked(product)) },
                                    cartProductIds = cartProductIds
                                )
                            } else {
                                Text(
                                    text = stringResource(R.string.no_exclusive_offers),
                                    style = MaterialTheme.appTextStyles.emptyStateText(),
                                    modifier = Modifier.padding(horizontal = MaterialTheme.appDimensions.dimen24)
                                )
                            }
                            
                            HeaderRow(R.string.best_selling) {
                                onSeeAllClick(SeeAllSectionType.BEST_SELLING)
                            }
                            if (state.bestSelling.isNotEmpty()) {
                                ProductsRow(
                                    products = state.bestSelling,
                                    onProductClick = onProductClick,
                                    onAddToCartClick = { product -> viewModel.onEvent(MainScreenEvent.OnAddToCartClicked(product)) },
                                    cartProductIds = cartProductIds
                                )
                            } else {
                                Text(
                                    text = stringResource(R.string.no_best_selling),
                                    style = MaterialTheme.appTextStyles.emptyStateText(),
                                    modifier = Modifier.padding(horizontal = MaterialTheme.appDimensions.dimen24)
                                )
                            }
                            
                            HeaderRow(R.string.groceries) {
                                onSeeAllClick(SeeAllSectionType.GROCERIES)
                            }
                            if (state.groceries.isNotEmpty()) {
                                ProductsRow(
                                    products = state.groceries,
                                    onProductClick = onProductClick,
                                    onAddToCartClick = { product -> viewModel.onEvent(MainScreenEvent.OnAddToCartClicked(product)) },
                                    cartProductIds = cartProductIds
                                )
                            } else {
                                Text(
                                    text = stringResource(R.string.no_grocery_products),
                                    style = MaterialTheme.appTextStyles.emptyStateText(),
                                    modifier = Modifier.padding(horizontal = MaterialTheme.appDimensions.dimen24)
                                )
                            }
                            Spacer(Modifier.height(MaterialTheme.appDimensions.dimen32))
                        }
                    }
                }
            }
        }
    }
}