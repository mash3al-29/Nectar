package com.mashaal.ecommerce_app.ui.ProductScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import com.mashaal.ecommerce_app.ui.theme.AppIcons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import android.content.Intent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.mashaal.ecommerce_app.ui.theme.*

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
    val dividerColor = DividerColor
    if (state.isLoading) {
        LoadingState()
    }
    if (state.error != null) {
        ErrorState(error = state.error)
        return
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClick()
                    }) {
                        Icon(AppIcons.Back, contentDescription = stringResource(R.string.back),Modifier.size(35.dp))
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        state.product?.let { product ->
                            val shareText = context.getString(
                                R.string.share_product_text,
                                product.name,
                                product.price
                            )
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, shareText)
                                type = "text/plain"
                            }
                            val chooser = Intent.createChooser(shareIntent, context.getString(R.string.share_product))
                            context.startActivity(chooser)
                        }
                    }) {
                        Icon(AppIcons.Share, contentDescription = stringResource(R.string.share))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White,
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(White),
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
                                .clip(RoundedCornerShape(bottomStart = 35.dp, bottomEnd = 35.dp))
                                .background(White),
                            contentScale = ContentScale.Crop
                        )

                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .background(White)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    state.product?.let { productItem ->
                        Text(
                            text = productItem.name,
                            fontFamily = GilroyBoldFont,
                            fontSize = 24.sp,
                            color = Black
                        )
                    }
                    IconButton(
                        onClick = { viewModel.onEvent(ProductScreenEvent.OnFavoriteToggled) },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(White)
                    ){
                        Icon(
                            imageVector = if (isFavorite) AppIcons.FavoriteFilled else AppIcons.FavoriteOutlined,
                            contentDescription = stringResource(R.string.favorite),
                            tint = if (isFavorite) ErrorColor else Gray
                        )
                    }
                }
                state.product?.let { productItem ->
                    Text(
                        text = productItem.detail,
                        fontFamily = GilroyMediumFont,
                        fontSize = 16.sp,
                        color = ProductDetailColor
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
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
                    priceFontSize = 24
                )
                Spacer(modifier = Modifier.height(24.dp))
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
                            fontFamily = GilroyRegularFont,
                            fontSize = 14.sp,
                            color = DarkGray,
                            lineHeight = 24.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                SectionDivider(dividerColor)
                CollapsibleSection(
                    title = stringResource(R.string.nutrition),
                    isExpanded = nutritionExpanded,
                    rotationValue = nutritionRotation,
                    onToggle = { nutritionExpanded = !nutritionExpanded }
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = White),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(vertical = 16.dp)) {
                            state.product?.let { productItem ->
                                productItem.nutrition.forEach { (nutrient, value) ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = nutrient,
                                            fontFamily = GilroyMediumFont,
                                            fontSize = 14.sp,
                                            color = DarkGray
                                        )
                                        Text(
                                            text = value,
                                            fontFamily = GilroyMediumFont,
                                            fontSize = 14.sp,
                                            color = DarkGray
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
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
                                    tint = if (index < productItem.review) StarColor else LightGray,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                GeneralButton(
                    onClick = { 
                        viewModel.onEvent(ProductScreenEvent.OnAddToCartClicked)
                    },
                    currentText = when {
                        state.addToCartSuccess -> R.string.added_to_cart
                        else -> R.string.add_to_basket
                    },
                    enabled = state.product != null,
                    containerColor = MainThemeColor,
                    textColor = White
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}