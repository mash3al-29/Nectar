package com.mashaal.ecommerce_app.ui.MyCartScreen

import androidx.compose.foundation.background
import com.mashaal.ecommerce_app.ui.Common.clickableNoRipple
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.AppAsyncImage
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
    val currentState by viewModel.state.collectAsStateWithLifecycle()
    var isCheckingOut by rememberSaveable { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()
    
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
                .padding(top = MaterialTheme.appDimensions.dimen32)
        ) {
            Column(
                modifier = Modifier.padding(
                    bottom = MaterialTheme.appDimensions.dimen24
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
                        .padding(top = MaterialTheme.appDimensions.dimen16),
                    thickness = MaterialTheme.appDimensions.dimen1,
                    color = MaterialTheme.appColors.cartDividerColor
                )
            }

            when (val state = currentState) {
                is MyCartScreenState.Loading -> {
                    LoadingState()
                }
                is MyCartScreenState.Error -> {
                    ErrorState(error = state.message)
                }
                is MyCartScreenState.Success -> {
                    if (state.cartItems.isEmpty()) {
                        EmptyState(message = stringResource(R.string.cart_empty))
                    } else {
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(
                                start = MaterialTheme.appDimensions.dimen32,
                                end = MaterialTheme.appDimensions.dimen32,
                            )
                        ) {
                            items(
                                items = state.cartItems,
                                key = { cartItem -> cartItem.product.id } // use the product ID as key to prevent recomposition accoridng to best practices
                            ) { cartItem ->
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
                            modifier = Modifier.padding(
                                start = MaterialTheme.appDimensions.dimen32, 
                                end = MaterialTheme.appDimensions.dimen32, 
                                bottom = MaterialTheme.appDimensions.dimen16
                            ),
                        ) {
                            Button(
                                onClick = {
                                    isCheckingOut = true
                                    viewModel.onEvent(MyCartScreenEvent.OnCheckoutClicked)
                                    navigateToOnAcceptedScreen(state.totalPrice)
                                },
                                enabled = !isCheckingOut,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(MaterialTheme.appDimensions.dimen60),
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
                                                horizontal = MaterialTheme.appDimensions.dimen8,
                                                vertical = MaterialTheme.appDimensions.dimen4
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
                modifier = Modifier.size(MaterialTheme.appDimensions.dimen60),
                contentAlignment = Alignment.Center
            ) {
                AppAsyncImage(
                    cartItem.product.imageUrl,
                    cartItem.product.description, Modifier.fillMaxSize(), ContentScale.Fit)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = MaterialTheme.appDimensions.dimen24)
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
                            modifier = Modifier.padding(top = MaterialTheme.appDimensions.dimen4)
                        )
                    }

                    Icon(
                        imageVector = AppIcons.Close,
                        contentDescription = stringResource(R.string.remove_item),
                        modifier = Modifier
                            .size(MaterialTheme.appDimensions.dimen24)
                            .clickableNoRipple { onRemoveItem() },
                        tint = MaterialTheme.appColors.removeIconColor
                    )
                }

                QuantityPriceRow(
                    quantity = cartItem.quantity,
                    price = "$${String.format(Locale.US,"%.2f", cartItem.totalPrice)}",
                    onQuantityDecrease = {
                        onQuantityChanged(cartItem.quantity - 1)
                    },
                    onQuantityIncrease = {
                        if (cartItem.quantity < 99) {
                            onQuantityChanged(cartItem.quantity + 1)
                        }
                    },
                    modifier = Modifier.padding(top = MaterialTheme.appDimensions.dimen16),
                    myCartRedirection = true
                )
            }
        }
        Box(
            modifier = Modifier.padding(
                start = MaterialTheme.appDimensions.dimen24,
                end = MaterialTheme.appDimensions.dimen24,
                top = MaterialTheme.appDimensions.dimen16
            )
        ) {
            SectionDivider(MaterialTheme.appColors.cartDividerColor)
        }
    }
}