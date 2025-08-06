package com.mashaal.ecommerce_app.ui.AcceptedOrderScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.mashaal.ecommerce_app.R
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.res.stringResource
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.GeneralButton
import com.mashaal.ecommerce_app.ui.theme.appColors
import com.mashaal.ecommerce_app.ui.theme.appDimensions
import com.mashaal.ecommerce_app.ui.theme.appTextStyles

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AcceptedOrderScreen(onBackToHome: () -> Unit, totalPrice: Double) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BlurredBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = MaterialTheme.appDimensions.dimen24),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier.padding(end = MaterialTheme.appDimensions.dimen24)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.accepted_order_image),
                    contentDescription = stringResource(R.string.accepted_order_image),
                    modifier = Modifier
                        .width(MaterialTheme.appDimensions.dimen350)
                        .height(MaterialTheme.appDimensions.dimen350)
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen24))
            Text(
                text = stringResource(R.string.order_accepted),
                style = MaterialTheme.appTextStyles.orderAcceptedTitle(),
                color = MaterialTheme.appColors.black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen16))
            Text(
                text = stringResource(R.string.items_placed),
                style = MaterialTheme.appTextStyles.orderAcceptedSubtitle(),
                color = MaterialTheme.appColors.searchTextColor,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen16))
            Text(
                text = stringResource(R.string.total_price, totalPrice),
                style = MaterialTheme.appTextStyles.orderAcceptedPrice(),
                color = MaterialTheme.appColors.black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen8))
            GeneralButton(
                onClick = {
                    onBackToHome()
                },
                currentText = R.string.back_to_home,
                containerColor = MaterialTheme.appColors.transparent,
                textColor = MaterialTheme.appColors.black
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun BlurredBackground(modifier: Modifier = Modifier) {
    val blurRadius = MaterialTheme.appDimensions.blurEffectRadius
    val gradientTop = MaterialTheme.appColors.gradientTop
    val gradientMiddle = MaterialTheme.appColors.gradientMiddle
    val gradientBottom = MaterialTheme.appColors.gradientBottom

    Box(
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer {
                renderEffect = RenderEffect
                    .createBlurEffect(
                        blurRadius,
                        blurRadius,
                        Shader.TileMode.CLAMP
                    )
                    .asComposeRenderEffect()
            }
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        gradientTop,
                        gradientMiddle,
                        gradientBottom
                    )
                )
            )
    )
}

