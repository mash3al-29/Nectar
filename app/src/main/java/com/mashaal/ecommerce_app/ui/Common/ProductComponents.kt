package com.mashaal.ecommerce_app.ui.Common

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.ui.theme.*

object ProductComponents {
@Composable
fun ProductItem(
    product: Product,
    onProductClick: (Product) -> Unit,
    onAddToCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(175.dp)
            .height(250.dp)
            .clickable { onProductClick(product) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
        border = BorderStroke(width = 1.dp, color = Color.hsl(0f, 0f, 0.89f, 1f)
        )
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
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.name,
                fontFamily = GilroyBoldFont,
                fontSize = 16.sp,
                color = Color.Black,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.detail,
                fontFamily = GilroyMediumFont,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 1
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${product.price}",
                    fontFamily = GilroyBoldFont,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Box(
                    modifier = Modifier
                        .padding(bottom = 8.dp, end = 8.dp)
                ) {
                    IconButton(
                        onClick = onAddToCartClick,
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = MainThemeColor,
                                shape = RoundedCornerShape(17.dp)
                            )
                    ) {
                        Icon(
                            imageVector = AppIcons.Add,
                            contentDescription = stringResource(R.string.add_to_basket),
                            tint = Color.White
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
    onAddToCartClick: () -> Unit,
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
            color = Color.Black
        )
        IconButton(
            onClick = onToggle,
            modifier = Modifier
                .size(width = 30.dp, height = 30.dp)
                .clip(CircleShape)
                .background(Color.White)
        ) {
            Icon(
                imageVector = AppIcons.RightArrow,
                contentDescription = if (isExpanded) stringResource(R.string.collapse) else stringResource(R.string.expand),
                modifier = Modifier.rotate(rotationValue).size(30.dp),
                tint = Color.Black
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
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            color = contentColor,
        fontWeight = FontWeight.Bold
    )
}
}

@Composable
fun SectionDivider(dividerColor: Color) {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth(),
        thickness = 1.dp,
        color = dividerColor
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun AddToBasketButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MainThemeColor)
    ) {
        Text(
            text = stringResource(R.string.add_to_basket),
            fontFamily = GilroyBoldFont,
            fontSize = 18.sp,
            color = Color.White
        )
    }
}
}