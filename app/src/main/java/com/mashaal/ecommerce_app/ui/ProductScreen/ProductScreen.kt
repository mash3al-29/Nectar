package com.mashaal.ecommerce_app.ui.ProductScreen

import androidx.compose.animation.core.animateFloatAsState
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.CollapsibleSection
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.GeneralButton
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.QuantityPriceRow
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.SectionDivider
import com.mashaal.ecommerce_app.ui.Common.LoadingState
import com.mashaal.ecommerce_app.ui.Common.ErrorState
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
    val state by viewModel.state.collectAsStateWithLifecycle()
    val quantity = state.quantity
    val isFavorite = state.isFavorite
    val context = LocalContext.current
    var productDetailExpanded by remember { mutableStateOf(false) }
    var nutritionExpanded by remember { mutableStateOf(false) }
    var reviewExpanded by remember { mutableStateOf(false) }
    val productDetailRotation by animateFloatAsState(targetValue = if (productDetailExpanded) 90f else 0f, label = "productDetailRotation")
    val nutritionRotation by animateFloatAsState(targetValue = if (nutritionExpanded) 90f else 0f, label = "nutritionRotation")
    val reviewRotation by animateFloatAsState(targetValue = if (reviewExpanded) 90f else 0f, label = "reviewRotation")
    val dividerColor = MaterialTheme.appColors.dividerColor
    
    LaunchedEffect(state.shareData) {
        state.shareData?.let { shareData ->
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
    
    if (state.isLoading) {
        LoadingState()
    }
    if (state.error != null) {
        ErrorState(error = state.error)
        return
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClick()
                    }) {
                        Icon(
                            AppIcons.Back,
                            contentDescription = stringResource(R.string.back),
                            Modifier.size(MaterialTheme.appDimensions.iconSizeLarge)
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
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.appColors.white)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.appDimensions.productImageHeightLarge)
                    .background(MaterialTheme.appColors.white),
                contentAlignment = Alignment.Center
            ) {
                    state.product?.let { product ->
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(product.imageUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = state.product?.name,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(MaterialTheme.appShapes.productImageContainer)
                                .background(MaterialTheme.appColors.white),
                            contentScale = ContentScale.Crop
                        )

                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.appDimensions.paddingLarge)
                    .background(MaterialTheme.appColors.white)
            ) {
                Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.paddingMedium))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    state.product?.let { productItem ->
                        Text(
                            text = productItem.name,
                            style = MaterialTheme.appTextStyles.productTitle()
                        )
                    }
                    IconButton(
                        onClick = { viewModel.onEvent(ProductScreenEvent.OnFavoriteToggled) },
                        modifier = Modifier
                            .size(MaterialTheme.appDimensions.iconButtonSizeLarge)
                            .clip(MaterialTheme.appShapes.iconButton)
                            .background(MaterialTheme.appColors.white)
                    ){
                        Icon(
                            imageVector = if (isFavorite) AppIcons.FavoriteFilled else AppIcons.FavoriteOutlined,
                            contentDescription = stringResource(R.string.favorite),
                            tint = if (isFavorite) MaterialTheme.appColors.errorColor else MaterialTheme.appColors.gray
                        )
                    }
                }
                state.product?.let { productItem ->
                    Text(
                        text = productItem.detail,
                        style = MaterialTheme.appTextStyles.productDetail()
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.paddingLarge))
                QuantityPriceRow(
                    quantity = quantity,
                    price = "$${state.totalPrice}",
                    onQuantityDecrease = {
                        if (quantity > 1) {
                            viewModel.onEvent(ProductScreenEvent.OnQuantityChanged(quantity - 1))
                        }
                    },
                    onQuantityIncrease = {
                        if (quantity < 99) {
                            viewModel.onEvent(ProductScreenEvent.OnQuantityChanged(quantity + 1))
                        }
                    },
                    priceFontSize = 24,
                    myCartRedirection = false
                )
                Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.paddingLarge))
                SectionDivider(dividerColor)
                CollapsibleSection(
                    title = stringResource(R.string.product_detail),
                    isExpanded = productDetailExpanded,
                    rotationValue = productDetailRotation,
                    onToggle = { productDetailExpanded = !productDetailExpanded }
                ) {
                    state.product?.let { productItem ->
                        Text(
                            text = productItem.description,
                            style = MaterialTheme.appTextStyles.productDescription()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.paddingMedium))
                SectionDivider(dividerColor)
                CollapsibleSection(
                    title = stringResource(R.string.nutrition),
                    isExpanded = nutritionExpanded,
                    rotationValue = nutritionRotation,
                    onToggle = { nutritionExpanded = !nutritionExpanded }
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.appColors.white),
                        shape = MaterialTheme.appShapes.cardSmall
                    ) {
                        Column(modifier = Modifier.padding(vertical = MaterialTheme.appDimensions.paddingMedium)) {
                            state.product?.let { productItem ->
                                productItem.nutrition.forEach { (nutrient, value) ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = MaterialTheme.appDimensions.spacingExtraSmall),
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
                }
                Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.paddingMedium))
                SectionDivider(dividerColor)
                CollapsibleSection(
                    title = stringResource(R.string.review),
                    isExpanded = reviewExpanded,
                    rotationValue = reviewRotation,
                    onToggle = { reviewExpanded = !reviewExpanded }
                ) {
                    Row {
                        state.product?.let { productItem ->
                            repeat(5) { index ->
                                Icon(
                                    imageVector = AppIcons.Star,
                                    contentDescription = null,
                                    tint = if (index < productItem.review) MaterialTheme.appColors.starColor else MaterialTheme.appColors.lightGray,
                                    modifier = Modifier.size(MaterialTheme.appDimensions.iconSize)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.paddingLarge))
                GeneralButton(
                    onClick = { 
                        viewModel.onEvent(ProductScreenEvent.OnAddToCartClicked)
                    },
                    currentText = when {
                        state.addToCartSuccess -> R.string.added_to_cart
                        else -> R.string.add_to_basket
                    },
                    enabled = state.product != null,
                    containerColor = MaterialTheme.appColors.primary,
                    textColor = MaterialTheme.appColors.white
                )
                Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.paddingLarge))
            }
        }
    }
}