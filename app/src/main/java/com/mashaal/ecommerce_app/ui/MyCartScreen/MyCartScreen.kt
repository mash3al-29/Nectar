package com.mashaal.ecommerce_app.ui.MyCartScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.QuantityPriceRow
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.SectionDivider
import com.mashaal.ecommerce_app.ui.Common.EmptyState
import com.mashaal.ecommerce_app.ui.Common.ErrorState
import com.mashaal.ecommerce_app.ui.Common.LoadingState
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.SVGImage
import com.mashaal.ecommerce_app.ui.theme.AppIcons
import com.mashaal.ecommerce_app.ui.theme.appColors
import com.mashaal.ecommerce_app.ui.theme.appDimensions
import com.mashaal.ecommerce_app.ui.theme.appShapes
import com.mashaal.ecommerce_app.ui.theme.appTextStyles
import java.util.Locale

@Composable
fun MyCartScreen(
    viewModel: MyCartScreenViewModel = hiltViewModel(),
        navigateToOnAcceptedScreen: (Double) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.appColors.white,
        contentColor = MaterialTheme.appColors.white,
        ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.appColors.white)
                .padding(paddingValues)
                .padding(top = MaterialTheme.appDimensions.spacingExtraLarge)
        ) {
            Column(
                modifier = Modifier.padding(
                    bottom = MaterialTheme.appDimensions.spacingLarge
                )
            ) {
                Text(
                    text = stringResource(R.string.my_cart),
                    style = MaterialTheme.appTextStyles.screenTitle(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.appDimensions.paddingMedium),
                    thickness = MaterialTheme.appDimensions.dividerThickness,
                    color = MaterialTheme.appColors.cartDividerColor
                )
            }

            if (state.isLoading) {
                LoadingState()
            } else if (state.error != null && state.error!!.isNotBlank()) {
                ErrorState(error = state.error!!)
            } else if (state.cartItems.isEmpty()) {
                EmptyState(message = stringResource(R.string.cart_empty))
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(
                        start = MaterialTheme.appDimensions.paddingExtraLarge,
                        end = MaterialTheme.appDimensions.paddingExtraLarge,
                    )
                ) {
                    items(state.cartItems) { cartItem ->
                        CartItemRow(
                            cartItem = cartItem,
                            onQuantityChanged = { newQuantity ->
                                viewModel.onEvent(MyCartScreenEvent.OnQuantityChanged(cartItem.product.id, newQuantity))
                            },
                            onRemoveItem = {
                                viewModel.onEvent(MyCartScreenEvent.OnRemoveItem(cartItem.product.id))
                            }
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(start = MaterialTheme.appDimensions.paddingExtraLarge, end = MaterialTheme.appDimensions.paddingExtraLarge, bottom = MaterialTheme.appDimensions.spacingMedium),
                ) {
                    Button(
                        onClick = {
                            viewModel.onEvent(MyCartScreenEvent.OnCheckoutClicked)
                            navigateToOnAcceptedScreen(state.totalPrice)
                                  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(MaterialTheme.appDimensions.buttonHeight),
                        shape = MaterialTheme.appShapes.button,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.appColors.primary)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.go_to_checkout),
                                style = MaterialTheme.appTextStyles.buttonText()
                            )
                            Box(
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.appColors.checkoutButtonColor,
                                        MaterialTheme.appShapes.cardSmall
                                    )
                                    .padding(
                                        horizontal = MaterialTheme.appDimensions.paddingSmall,
                                        vertical = MaterialTheme.appDimensions.paddingExtraSmall
                                    )
                            ) {
                                Text(
                                    text = "$${String.format(Locale.US, "%.2f", state.totalPrice)}",
                                    style = MaterialTheme.appTextStyles.smallButtonText()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    }


@Composable
fun CartItemRow(
    cartItem: CartItem,
    onQuantityChanged: (Int) -> Unit,
    onRemoveItem: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier.size(MaterialTheme.appDimensions.myCartProductImageHeight),
                contentAlignment = Alignment.Center
            ) {
                SVGImage(data = cartItem.product.imageUrl, contentDescription = cartItem.product.description)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = MaterialTheme.appDimensions.paddingLarge)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = cartItem.product.name,
                            style = MaterialTheme.appTextStyles.cartItemName()
                        )
                        Text(
                            text = cartItem.portion,
                            style = MaterialTheme.appTextStyles.cartItemPortion(),
                            modifier = Modifier.padding(top = MaterialTheme.appDimensions.paddingExtraSmall)
                        )
                    }

                    Icon(
                        imageVector = AppIcons.Close,
                        contentDescription = stringResource(R.string.remove_item),
                        modifier = Modifier
                            .size(MaterialTheme.appDimensions.iconSizeMedium)
                            .clickable { onRemoveItem() },
                        tint = MaterialTheme.appColors.removeIconColor
                    )
                }

                QuantityPriceRow(
                    quantity = cartItem.quantity,
                    price = "$${String.format(Locale.US,"%.2f", cartItem.totalPrice)}",
                    onQuantityDecrease = {
                        if (cartItem.quantity > 1) {
                            onQuantityChanged(cartItem.quantity - 1)
                        }
                    },
                    onQuantityIncrease = {
                        if (cartItem.quantity < 99) {
                            onQuantityChanged(cartItem.quantity + 1)
                        }
                    },
                    modifier = Modifier.padding(top = MaterialTheme.appDimensions.paddingMedium),
                    myCartRedirection = true
                )
            }
        }
        Box(
            modifier = Modifier.padding(
                start = MaterialTheme.appDimensions.paddingLarge,
                end = MaterialTheme.appDimensions.paddingLarge,
                top = MaterialTheme.appDimensions.paddingMedium
            )
        ) {
            SectionDivider(MaterialTheme.appColors.cartDividerColor)
        }
    }
}