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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.ui.theme.AppIcons
import com.mashaal.ecommerce_app.ui.theme.appColors
import com.mashaal.ecommerce_app.ui.theme.appDimensions
import com.mashaal.ecommerce_app.ui.theme.appShapes
import com.mashaal.ecommerce_app.ui.theme.appTextStyles
import kotlinx.coroutines.delay
import androidx.compose.foundation.border

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
        shape = MaterialTheme.appShapes.card,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.appColors.cardBackground),
        border = BorderStroke(width = MaterialTheme.appDimensions.borderWidth, color = MaterialTheme.appColors.lightGray),
        modifier = modifier
            .width(MaterialTheme.appDimensions.productCardWidth)
            .height(MaterialTheme.appDimensions.productCardHeight)
            .clickable { onProductClick(product) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.appDimensions.spacingSmall),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.appDimensions.productImageHeight),
                contentAlignment = Alignment.Center
            ) {
                SVGImage(data = product.imageUrl, contentDescription = product.description)
            }
            Text(
                text = product.name,
                style = MaterialTheme.appTextStyles.productName(),
                maxLines = 1,
                modifier = Modifier.padding(top = MaterialTheme.appDimensions.spacingSmall)
            )
            Text(
                text = product.detail,
                style = MaterialTheme.appTextStyles.productDetail(),
                maxLines = 1,
                modifier = Modifier.padding(top = MaterialTheme.appDimensions.spacingExtraSmall)
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.appTextStyles.price()
                )
                Box(
                    modifier = Modifier
                        .padding(
                            bottom = MaterialTheme.appDimensions.spacingSmall,
                            end = MaterialTheme.appDimensions.spacingSmall
                        )
                ) {
                    IconButton(
                        onClick = { 
                            isAdding = true
                            onAddToCartClick(product)
                        },
                        modifier = Modifier
                            .size(MaterialTheme.appDimensions.iconButtonSize)
                            .background(
                                color = MaterialTheme.appColors.primary,
                                shape = MaterialTheme.appShapes.iconButton
                            )
                    ) {
                        Icon(
                            imageVector = if (isAdding) AppIcons.Check else AppIcons.Add,
                            contentDescription = stringResource(R.string.add_to_basket),
                            tint = MaterialTheme.appColors.white
                        )
                    }
                }
            }
        }
    }
}

    @Composable
    fun SVGImage(data: String, contentDescription : String) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
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
        contentPadding = PaddingValues(horizontal = MaterialTheme.appDimensions.paddingMedium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.paddingMedium)
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
            style = MaterialTheme.appTextStyles.sectionHeader()
        )
        IconButton(
            onClick = onToggle,
            modifier = Modifier
                .size(MaterialTheme.appDimensions.iconSize)
                .background(MaterialTheme.appColors.white)
                .clip(CircleShape)
        ) {
            Icon(
                imageVector = AppIcons.RightArrow,
                contentDescription = if (isExpanded) stringResource(R.string.collapse) else stringResource(R.string.expand),
                modifier = Modifier
                    .rotate(rotationValue)
                    .size(MaterialTheme.appDimensions.iconSize),
                tint = MaterialTheme.appColors.black
            )
        }
    }
    
    AnimatedVisibility(
        visible = isExpanded,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Column(modifier = Modifier.padding(horizontal = MaterialTheme.appDimensions.paddingLarge)) {
            Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.spacingSmall))
            content()
        }
    }
}

@Composable
fun QuantityButton(
    text: String,
    backgroundColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
    myCartRedirection: Boolean
) {
    Card(
        shape = MaterialTheme.appShapes.quantityButton,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = if (myCartRedirection) BorderStroke(
            width = MaterialTheme.appDimensions.borderWidth,
            color = MaterialTheme.appColors.cartDividerColor
        ) else null,
        modifier = Modifier
            .size(MaterialTheme.appDimensions.quantityButtonSize)
            .clickable(onClick = onClick),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.appDimensions.quantityButtonContentPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.appTextStyles.quantityButton().copy(color = contentColor),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier.wrapContentSize()
                )
            }
        }
    )
}

@Composable
fun SectionDivider(dividerColor: Color) {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = MaterialTheme.appDimensions.paddingMedium),
        thickness = MaterialTheme.appDimensions.dividerThickness,
        color = dividerColor
    )
}

@Composable
fun GeneralButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes currentText: Int,
    enabled: Boolean = true,
    containerColor : Color,
    textColor : Color
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(MaterialTheme.appDimensions.buttonHeight),
        shape = MaterialTheme.appShapes.button,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = containerColor.copy(alpha = 0.6f)
        )
    ) {
        Text(
            text = stringResource(currentText),
            style = MaterialTheme.appTextStyles.buttonText().copy(
                color = if (enabled) textColor else textColor.copy(alpha = 0.7f),
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
    priceFontSize: Int = 18,
    myCartRedirection: Boolean
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.spacingSmall)
        ) {
            QuantityButton(
                text = "−",
                backgroundColor = MaterialTheme.appColors.white,
                contentColor = MaterialTheme.appColors.gray,
                onClick = onQuantityDecrease,
                myCartRedirection = myCartRedirection
            )
            Box(
                modifier = Modifier.border(if (!myCartRedirection) BorderStroke(
                    width = MaterialTheme.appDimensions.borderWidth,
                    color = MaterialTheme.appColors.cartDividerColor,
                ) else BorderStroke(
                    width = MaterialTheme.appDimensions.borderWidth,
                    color = MaterialTheme.appColors.white,
                    ), shape = MaterialTheme.appShapes.quantityButton,)
                    .fillMaxHeight(),
            ) {
                Text(
                    text = quantity.toString(),
                    style = MaterialTheme.appTextStyles.quantityText(),
                    modifier = Modifier
                        .width(MaterialTheme.appDimensions.quantityTextSize)
                        .background(
                            MaterialTheme.appColors.white,
                            MaterialTheme.appShapes.quantityButtonText
                        )
                        .padding(vertical = MaterialTheme.appDimensions.paddingSmallInBetween),
                    textAlign = TextAlign.Center
                )
            }
            QuantityButton(
                text = "+",
                backgroundColor = MaterialTheme.appColors.white,
                contentColor = MaterialTheme.appColors.primary,
                onClick = onQuantityIncrease,
                myCartRedirection = myCartRedirection
            )
        }
        
        Text(
            text = price,
            style = if (priceFontSize == 24) MaterialTheme.appTextStyles.largePrice() else MaterialTheme.appTextStyles.priceDetail(),
            maxLines = 1
        )
    }
}
}