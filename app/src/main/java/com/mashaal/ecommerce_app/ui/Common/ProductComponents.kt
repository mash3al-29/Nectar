package com.mashaal.ecommerce_app.ui.Common

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.ui.theme.*
import kotlinx.coroutines.delay

object ProductComponents {
@Composable
fun ProductItem(
    product: Product,
    onProductClick: (Product) -> Unit,
    onAddToCartClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    var isAdding by remember { mutableStateOf(false) }

    LaunchedEffect(isAdding) {
        if (isAdding) {
            delay(1500)
            isAdding = false
        }
    }
    Card(
        modifier = modifier
            .width(175.dp)
            .height(250.dp)
            .clickable { onProductClick(product) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
        border = BorderStroke(width = 1.dp, color = LightGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = product.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                text = product.name,
                style = AppTextStyles.ProductName,
                maxLines = 1,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = product.detail,
                style = AppTextStyles.ProductDetail,
                maxLines = 1,
                modifier = Modifier.padding(top = 4.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${product.price}",
                    style = AppTextStyles.Price
                )
                Box(
                    modifier = Modifier
                        .padding(
                            bottom = 8.dp,
                            end = 8.dp
                        )
                ) {
                    IconButton(
                        onClick = { 
                            isAdding = true
                            onAddToCartClick(product)
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = if (isAdding) SuccessColor else MainThemeColor,
                                shape = RoundedCornerShape(17.dp)
                            )
                    ) {
                        Icon(
                            imageVector = if (isAdding) AppIcons.Check else AppIcons.Add,
                            contentDescription = stringResource(R.string.add_to_basket),
                            tint = White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductsRow(
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onAddToCartClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products) { product ->
            ProductItem(
                product = product,
                onProductClick = onProductClick,
                onAddToCartClick = onAddToCartClick
            )
        }
    }
}

@Composable
fun CollapsibleSection(
    title: String,
    isExpanded: Boolean,
    rotationValue: Float,
    onToggle: () -> Unit,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontFamily = GilroyBoldFont,
            fontSize = 18.sp,
            color = Black
        )
        IconButton(
            onClick = onToggle,
            modifier = Modifier
                .size(width = 30.dp, height = 30.dp)
                .clip(CircleShape)
                .background(White)
        ) {
            Icon(
                imageVector = AppIcons.RightArrow,
                contentDescription = if (isExpanded) stringResource(R.string.collapse) else stringResource(R.string.expand),
                modifier = Modifier.rotate(rotationValue).size(30.dp),
                tint = Black
            )
        }
    }
    
    AnimatedVisibility(
        visible = isExpanded,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Column(modifier = Modifier.padding(horizontal = 25.dp)) {
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
fun QuantityButton(
    text: String,
    backgroundColor: Color,
    contentColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = AppTextStyles.QuantityButton.copy(color = contentColor),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SectionDivider(dividerColor: Color) {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        thickness = 1.dp,
        color = dividerColor
    )
}

@Composable
fun GeneralButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes currentText: Int,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MainThemeColor,
            disabledContainerColor = MainThemeColor.copy(alpha = 0.6f)
        )
    ) {
        Text(
            text = stringResource(currentText),
            style = AppTextStyles.ButtonText.copy(
                color = if (enabled) White else White.copy(alpha = 0.7f)
            )
        )
    }
}

@Composable
fun QuantityPriceRow(
    quantity: Int,
    price: String,
    onQuantityDecrease: () -> Unit,
    onQuantityIncrease: () -> Unit,
    modifier: Modifier = Modifier,
    priceFontSize: Int = 18
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QuantityButton(
                text = "−",
                backgroundColor = White,
                contentColor = Gray,
                onClick = onQuantityDecrease
            )
            Text(
                text = "$quantity",
                style = AppTextStyles.QuantityText,
                modifier = Modifier
                    .width(50.dp)
                    .background(
                        White,
                        RoundedCornerShape(15.dp)
                    )
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center
            )
            QuantityButton(
                text = "+",
                backgroundColor = White,
                contentColor = MainThemeColor,
                onClick = onQuantityIncrease
            )
        }
        
        Text(
            text = price,
            style = if (priceFontSize == 24) AppTextStyles.LargePrice else AppTextStyles.PriceDetail,
            maxLines = 1
        )
    }
}
}