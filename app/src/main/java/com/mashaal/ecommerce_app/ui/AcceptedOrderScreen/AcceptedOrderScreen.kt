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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.theme.Black
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.GeneralButton
import com.mashaal.ecommerce_app.ui.theme.SearchTextColor
import com.mashaal.ecommerce_app.ui.theme.Typography


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AcceptedOrderScreen(onBackToHome: () -> Unit, totalPrice: Double,) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BlurredBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier.padding(end = 30.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.accepted_order_image),
                    contentDescription = "Accepted Order Image",
                    modifier = Modifier
                        .width(350.dp)
                        .height(350.dp)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.order_accepted),
                style = Typography.bodyLarge.copy(fontSize = 28.sp),
                color = Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.items_placed),
                style = Typography.bodySmall.copy(fontSize = 16.sp),
                color = SearchTextColor,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.total_price, totalPrice),
                style = Typography.titleMedium.copy(fontSize = 18.sp),
                color = Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            GeneralButton(
                onClick = {
                    onBackToHome()
                },
                currentText = R.string.back_to_home,
                containerColor = Color.Transparent,
                textColor = Black
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun BlurredBackground(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer {
                renderEffect = RenderEffect
                    .createBlurEffect(30f, 30f, Shader.TileMode.CLAMP)
                    .asComposeRenderEffect()
            }
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFE5E5),
                        Color(0xFFE5FFF3),
                        Color(0xFFE5F0FF)
                    )
                )
            )
    )
}
