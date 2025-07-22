package com.mashaal.ecommerce_app.ui.ProductScreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mashaal.ecommerce_app.ui.theme.GilroyBoldFont
import com.mashaal.ecommerce_app.ui.theme.GilroyMediumFont
import com.mashaal.ecommerce_app.ui.theme.GilroyRegularFont
import com.mashaal.ecommerce_app.ui.theme.MainThemeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onAddToCartClick: () -> Unit,
    viewModel: ProductScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val quantity = state.quantity
    val isFavorite = state.isFavorite
    var productDetailExpanded by remember { mutableStateOf(false) }
    var nutritionExpanded by remember { mutableStateOf(false) }
    var reviewExpanded by remember { mutableStateOf(false) }
    val productDetailRotation by animateFloatAsState(targetValue = if (productDetailExpanded) 90f else 0f, label = "productDetailRotation")
    val nutritionRotation by animateFloatAsState(targetValue = if (nutritionExpanded) 90f else 0f, label = "nutritionRotation")
    val reviewRotation by animateFloatAsState(targetValue = if (reviewExpanded) 90f else 0f, label = "reviewRotation")
    val dividerColor = Color(0xB3E3E3E3) // hsla(0, 0%, 89%, 0.7)
    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MainThemeColor)
        }
        return
    }
    if (state.error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Error: ${state.error}",
                color = Color.Red,
                style = TextStyle(fontFamily = GilroyMediumFont, fontSize = 16.sp)
            )
        }
        return
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.onEvent(ProductScreenEvent.OnBackClicked)
                        onBackClick()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Share functionality */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Share")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.White),
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
                                .background(Color.White),
                            contentScale = ContentScale.Crop
                        )

                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .background(Color.White)
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
                            color = Color.Black
                        )
                    }
                    IconButton(
                        onClick = { viewModel.onEvent(ProductScreenEvent.OnFavoriteToggled) },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                    ){
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color.Red else Color.Gray
                        )
                    }
                }
                state.product?.let { productItem ->
                    Text(
                        text = productItem.detail,
                        fontFamily = GilroyMediumFont,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        QuantityButton(
                            text = "−",
                            backgroundColor = Color.White,
                            contentColor = Color.hsl(0f, 0f, 0.7f, 1f),
                            onClick = {
                                if (quantity > 1) {
                                    viewModel.onEvent(ProductScreenEvent.OnQuantityChanged(quantity - 1))
                                }
                            }
                        )
                        Text(
                            text = "$quantity",
                            fontFamily = GilroyBoldFont,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .width(60.dp)
                                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(15.dp))
                                .padding(vertical = 12.dp, horizontal = 15.dp),
                            textAlign = TextAlign.Center
                        )
                        QuantityButton(
                            text = "+",
                            backgroundColor = Color.White,
                            contentColor = MainThemeColor,
                            onClick = {
                                if (quantity < 99) {
                                    viewModel.onEvent(ProductScreenEvent.OnQuantityChanged(quantity + 1))
                                }
                            }
                        )
                    }
                    Column {
                        Text(
                            text = "$${state.totalPrice}",
                            fontFamily = GilroyBoldFont,
                            fontSize = 24.sp,
                            color = Color.Black
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                SectionDivider(dividerColor)
                CollapsibleSection(
                    title = "Product Detail",
                    isExpanded = productDetailExpanded,
                    rotationValue = productDetailRotation,
                    onToggle = { productDetailExpanded = !productDetailExpanded }
                ) {
                    state.product?.let { productItem ->
                        Text(
                            text = productItem.description,
                            fontFamily = GilroyRegularFont,
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            lineHeight = 24.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                SectionDivider(dividerColor)
                CollapsibleSection(
                    title = "Nutritions",
                    isExpanded = nutritionExpanded,
                    rotationValue = nutritionRotation,
                    onToggle = { nutritionExpanded = !nutritionExpanded }
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                                            color = Color.DarkGray
                                        )
                                        Text(
                                            text = value,
                                            fontFamily = GilroyMediumFont,
                                            fontSize = 14.sp,
                                            color = Color.DarkGray
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
                    title = "Review",
                    isExpanded = reviewExpanded,
                    rotationValue = reviewRotation,
                    onToggle = { reviewExpanded = !reviewExpanded }
                ) {
                    Row {
                        state.product?.let { productItem ->
                            repeat(5) { index ->
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = if (index < productItem.review) Color.hsl(11f, 0.88f, 0.60f, 1f) else Color.LightGray,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                AddToBasketButton(
                    onClick = { 
                        viewModel.onEvent(ProductScreenEvent.OnAddToCartClicked)
                        onAddToCartClick()
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}