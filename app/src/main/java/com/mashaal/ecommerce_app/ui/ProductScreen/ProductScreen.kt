package com.mashaal.ecommerce_app.ui.ProductScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import android.content.Intent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.CollapsibleSection
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.GeneralButton
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.QuantityPriceRow
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.SectionDivider
import com.mashaal.ecommerce_app.ui.Common.LoadingState
import com.mashaal.ecommerce_app.ui.Common.ErrorState
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.AppAsyncImage
import com.mashaal.ecommerce_app.ui.theme.AppIcons
import com.mashaal.ecommerce_app.ui.theme.appColors
import com.mashaal.ecommerce_app.ui.theme.appDimensions
import com.mashaal.ecommerce_app.ui.theme.appShapes
import com.mashaal.ecommerce_app.ui.theme.appTextStyles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: ProductScreenViewModel = hiltViewModel(),
) {
    val currentState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val dividerColor = MaterialTheme.appColors.dividerColor
    
    LaunchedEffect(currentState) {
        if (currentState is ProductScreenState.Success) {
            val successState = currentState as ProductScreenState.Success
            successState.shareData?.let { shareData ->
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareData.text)
                    type = "text/plain"
                }
                val chooser = Intent.createChooser(shareIntent, shareData.title)
                context.startActivity(chooser)
                viewModel.onEvent(ProductScreenEvent.OnShareConsumed)
            }
        }
    }
    
    when (val state = currentState) {
        is ProductScreenState.Loading -> {
            LoadingState()
        }
        is ProductScreenState.Error -> {
            ErrorState(error = state.message)
        }
        is ProductScreenState.Success -> {
            val product = state.product
            if (product == null) {
                ErrorState(error = stringResource(R.string.product_not_found))
                return
            }
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { },
                        navigationIcon = {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    AppIcons.Back,
                                    contentDescription = stringResource(R.string.back),
                                    Modifier.size(MaterialTheme.appDimensions.dimen28)
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                viewModel.onEvent(ProductScreenEvent.OnShareClicked)
                            }) {
                                Icon(AppIcons.Share, contentDescription = stringResource(R.string.share))
                            }
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.appColors.white,
                        ),
                        windowInsets = WindowInsets(0)
                    )
                },
                contentWindowInsets = WindowInsets(0, 0, 0, 0)
            ) { paddingValues ->
                val scrollState = rememberScrollState()

                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .background(MaterialTheme.appColors.white)
                        .verticalScroll(scrollState)
                        .padding(paddingValues)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(MaterialTheme.appDimensions.dimen300)
                            .background(MaterialTheme.appColors.white),
                        contentAlignment = Alignment.Center
                    ) {
                        AppAsyncImage(
                            imageUrl = product.imageUrl,
                            contentDescription = product.description,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(MaterialTheme.appShapes.productImageContainer),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.appDimensions.dimen24)
                            .background(MaterialTheme.appColors.white)
                    ) {
                        Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen16))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = product.name,
                                style = MaterialTheme.appTextStyles.productTitle()
                            )
                            IconButton(
                                onClick = { viewModel.onEvent(ProductScreenEvent.OnFavoriteToggled) },
                                modifier = Modifier
                                    .size(MaterialTheme.appDimensions.dimen48)
                                    .clip(MaterialTheme.appShapes.iconButton)
                                    .background(MaterialTheme.appColors.white)
                            ){
                                Icon(
                                    imageVector = if (state.isFavorite) AppIcons.FavoriteFilled else AppIcons.FavoriteOutlined,
                                    contentDescription = stringResource(R.string.favorite),
                                    tint = if (state.isFavorite) MaterialTheme.appColors.errorColor else MaterialTheme.appColors.gray
                                )
                            }
                        }
                        Text(
                            text = product.detail,
                            style = MaterialTheme.appTextStyles.productDetail()
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen24))
                        QuantityPriceRow(
                            quantity = state.quantity,
                            price = "$${state.totalPrice}",
                            onQuantityDecrease = {
                                if (state.quantity > 1) {
                                    viewModel.onEvent(ProductScreenEvent.OnQuantityChanged(state.quantity - 1))
                                }
                            },
                            onQuantityIncrease = {
                                if (state.quantity < 99) {
                                    viewModel.onEvent(ProductScreenEvent.OnQuantityChanged(state.quantity + 1))
                                }
                            },
                            priceFontSize = 24,
                            myCartRedirection = false
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen24))
                        SectionDivider(dividerColor)
                        CollapsibleSection(
                            title = stringResource(R.string.product_detail)
                        ) {
                            Text(
                                text = product.description,
                                style = MaterialTheme.appTextStyles.productDescription()
                            )
                        }
                        Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen16))
                        SectionDivider(dividerColor)
                        CollapsibleSection(
                            title = stringResource(R.string.nutrition)
                        ) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.appColors.white),
                                shape = MaterialTheme.appShapes.cardSmall
                            ) {
                                Column(modifier = Modifier.padding(vertical = MaterialTheme.appDimensions.dimen16)) {
                                    product.nutrition.forEach { (nutrient, value) ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = MaterialTheme.appDimensions.dimen8),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = nutrient,
                                                style = MaterialTheme.appTextStyles.nutritionLabel()
                                            )
                                            Text(
                                                text = value,
                                                style = MaterialTheme.appTextStyles.nutritionLabel()
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen16))
                        SectionDivider(dividerColor)
                        CollapsibleSection(
                            title = stringResource(R.string.review)
                        ) {
                            Row {
                                repeat(5) { index ->
                                    Icon(
                                        imageVector = AppIcons.Star,
                                        contentDescription = null,
                                        tint = if (index < product.review) MaterialTheme.appColors.starColor else MaterialTheme.appColors.lightGray,
                                        modifier = Modifier.size(MaterialTheme.appDimensions.dimen24)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen24))
                        GeneralButton(
                            onClick = {
                                if (!state.isInCart) {
                                    viewModel.onEvent(ProductScreenEvent.OnAddToCartClicked)
                                }
                            },
                            currentText = when {
                                state.isInCart -> R.string.added_to_cart
                                state.addToCartSuccess -> R.string.added_to_cart
                                else -> R.string.add_to_basket
                            },
                            containerColor = if (state.isInCart) MaterialTheme.appColors.checkoutButtonColor else MaterialTheme.appColors.primary,
                            textColor = MaterialTheme.appColors.white,
                            modifier = Modifier.padding(bottom = MaterialTheme.appDimensions.dimen60)
                        )
                    }
                }
            }
        }
    }
}