package com.mashaal.ecommerce_app.ui.OnStartScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.theme.GilroyMediumFont
import com.mashaal.ecommerce_app.ui.theme.GilroyRegularFont
import com.mashaal.ecommerce_app.ui.theme.White

@Composable
fun OnStartScreen(onNavigateToMainScreen: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_onstart),
            contentDescription = "Delivery giving groceries to customer",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Scaffold(
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.carrot),
                    contentDescription = "Carrot Logo",
                    modifier = Modifier
                        .width(150.dp)
                        .height(100.dp)
                        .padding(bottom = 30.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = stringResource(R.string.welcome_text),
                    fontWeight = FontWeight.W600,
                    fontFamily = GilroyRegularFont,
                    fontSize = 48.sp,
                    lineHeight = 50.sp,
                    color = White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = stringResource(R.string.welcome_subtext),
                    fontFamily = GilroyMediumFont,
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    lineHeight = 15.sp,
                    color = colorResource(R.color.subtext_color),
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {
                        onNavigateToMainScreen()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.main_theme_color)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .height(90.dp)
                        .padding(top = 35.dp)
                ) {
                    Text(
                        text = stringResource(R.string.welcome_button_text),
                        fontFamily = GilroyMediumFont,
                        fontSize = 18.sp,
                        lineHeight = 18.sp,
                        color = colorResource(R.color.button_text_color),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
