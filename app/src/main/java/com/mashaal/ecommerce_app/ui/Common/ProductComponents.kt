package com.mashaal.ecommerce_app.ui.Common

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.annotation.DrawableRes
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
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

object ProductComponents {
@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    product: Product,
    onProductClick: (Product) -> Unit,
    onAddToCartClick: (Product) -> Unit,
    isInCart: Boolean = false,
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
        border = BorderStroke(width = MaterialTheme.appDimensions.dimen1, color = MaterialTheme.appColors.lightGray),
        modifier = modifier
            .width(MaterialTheme.appDimensions.dimen175)
            .height(MaterialTheme.appDimensions.dimen250)
            .clickableNoRipple { onProductClick(product) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.appDimensions.dimen8),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.appDimensions.dimen100),
                contentAlignment = Alignment.Center
            ) {
                AppAsyncImage(
                    imageUrl = product.imageUrl,
                    contentDescription = product.description,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen8))
            Text(
                text = product.name,
                style = MaterialTheme.appTextStyles.productName()
            )
            Text(
                text = product.detail,
                style = MaterialTheme.appTextStyles.productDetail()
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
                            bottom = MaterialTheme.appDimensions.dimen8,
                            end = MaterialTheme.appDimensions.dimen8
                        )
                ) {
                    IconButton(
                        onClick = { 
                            if (!isInCart) {
                                isAdding = true
                                onAddToCartClick(product)
                            }
                        },
                        modifier = Modifier
                            .size(MaterialTheme.appDimensions.dimen45)
                            .background(
                                color = if (isInCart) MaterialTheme.appColors.checkoutButtonColor else MaterialTheme.appColors.primary,
                                shape = MaterialTheme.appShapes.iconButton
                            )
                    ) {
                        Icon(
                            imageVector = when {
                                isInCart -> AppIcons.Check
                                isAdding -> AppIcons.Check
                                else -> AppIcons.Add
                            },
                            contentDescription = if (isInCart) stringResource(R.string.added_to_cart) else stringResource(R.string.add_to_basket),
                            tint = MaterialTheme.appColors.white
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppAsyncImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .apply {
                placeholder?.let { placeholder(it) }
                error?.let { error(it) } ?: placeholder?.let { error(it) }
            }
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}

@Composable
fun ProductsRow(
    modifier: Modifier = Modifier,
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onAddToCartClick: (Product) -> Unit,
    cartProductIds: Set<Int> = emptySet(),
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = MaterialTheme.appDimensions.dimen16),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.dimen16)
    ) {
        items(products) { product ->
            ProductItem(
                product = product,
                onProductClick = onProductClick,
                onAddToCartClick = onAddToCartClick,
                isInCart = cartProductIds.contains(product.id)
            )
        }
    }
}

@Composable
fun CollapsibleSection(
    title: String,
    content: @Composable () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    val rotationValue by animateFloatAsState(
        targetValue = if (isExpanded) 90f else 0f,
                        label = stringResource(R.string.rotation_animation_label)
    )
    
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
            onClick = { isExpanded = !isExpanded },
            modifier = Modifier
                .size(MaterialTheme.appDimensions.dimen24)
                .background(MaterialTheme.appColors.white)
                .clip(CircleShape)
        ) {
            Icon(
                imageVector = AppIcons.RightArrow,
                contentDescription = if (isExpanded) stringResource(R.string.collapse) else stringResource(R.string.expand),
                modifier = Modifier
                    .rotate(rotationValue)
                    .size(MaterialTheme.appDimensions.dimen24),
                tint = MaterialTheme.appColors.black
            )
        }
    }
    
    AnimatedVisibility(
        visible = isExpanded,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Column(modifier = Modifier.padding(horizontal = MaterialTheme.appDimensions.dimen24)) {
            Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen8))
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
            width = MaterialTheme.appDimensions.dimen1,
            color = MaterialTheme.appColors.cartDividerColor
        ) else null,
        modifier = Modifier
            .size(MaterialTheme.appDimensions.dimen40)
            .clickableNoRipple(onClick),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.appDimensions.dimen10),
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
            .padding(bottom = MaterialTheme.appDimensions.dimen16),
        thickness = MaterialTheme.appDimensions.dimen1,
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
            .height(MaterialTheme.appDimensions.dimen60),
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
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.dimen8)
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
                    width = MaterialTheme.appDimensions.dimen1,
                    color = MaterialTheme.appColors.cartDividerColor,
                ) else BorderStroke(
                    width = MaterialTheme.appDimensions.dimen1,
                    color = MaterialTheme.appColors.white,
                    ), shape = MaterialTheme.appShapes.quantityButton,)
                    .fillMaxHeight(),
            ) {
                Text(
                    text = quantity.toString(),
                    style = MaterialTheme.appTextStyles.quantityText(),
                    modifier = Modifier
                        .width(MaterialTheme.appDimensions.dimen50)
                        .background(
                            MaterialTheme.appColors.white,
                            MaterialTheme.appShapes.quantityButtonText
                        )
                        .padding(vertical = MaterialTheme.appDimensions.dimen14),
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