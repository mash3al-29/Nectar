package com.mashaal.ecommerce_app.ui.MyCartScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.QuantityPriceRow
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.SectionDivider
import com.mashaal.ecommerce_app.ui.Common.EmptyState
import com.mashaal.ecommerce_app.ui.theme.*
import java.util.Locale

@Composable
fun MyCartScreen(
    viewModel: MyCartScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = White,
        contentColor = White,
        ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(paddingValues)
                .padding(top = 50.dp)
        ) {
            Column(
                modifier = Modifier.padding(
                    bottom = 30.dp
                )
            ) {
                Text(
                    text = stringResource(R.string.my_cart),
                    style = AppTextStyles.ScreenTitle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    thickness = 1.dp,
                    color = CartDividerColor
                )
            }

            if (state.cartItems.isEmpty()) {
                EmptyState(message = stringResource(R.string.cart_empty))
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(
                        start = 25.dp,
                        end = 25.dp,
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
                    modifier = Modifier.padding(horizontal = 25.dp)
                ) {
                    Button(
                        onClick = { viewModel.onEvent(MyCartScreenEvent.OnCheckoutClicked) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MainThemeColor)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.go_to_checkout),
                                style = AppTextStyles.ButtonText
                            )
                            Box(
                                modifier = Modifier
                                    .background(
                                        CheckoutButtonColor,
                                        RoundedCornerShape(4.dp)
                                    )
                                    .padding(
                                        horizontal = 8.dp,
                                        vertical = 4.dp
                                    )
                            ) {
                                Text(
                                    text = "$${String.format(Locale.US, "%.2f", state.totalPrice)}",
                                    style = AppTextStyles.SmallButtonText
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
                modifier = Modifier.size(70.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(cartItem.product.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = cartItem.product.name,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 24.dp)
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
                            style = AppTextStyles.CartItemName
                        )
                        Text(
                            text = cartItem.portion,
                            style = AppTextStyles.CartItemPortion,
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                    
                    Icon(
                        imageVector = AppIcons.Close,
                        contentDescription = stringResource(R.string.remove_item),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onRemoveItem() },
                        tint = RemoveIconColor
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
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
        Box(
            modifier = Modifier.padding(
                start = 24.dp,
                end = 24.dp,
                top = 16.dp
            )
        ) {
            SectionDivider(CartDividerColor)
        }
    }
} 