package com.mashaal.ecommerce_app.ui.SplashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.theme.White
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToHomeFromSplash: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onNavigateToHomeFromSplash()
    }

    Scaffold(
        containerColor = colorResource(id = R.color.main_theme_color),
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.carrot),
                contentDescription = "Carrot Logo",
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
            )

            Column {
                Image(
                    painter = painterResource(id = R.drawable.nectar_logo),
                    contentDescription = "Nectar Logo",
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp)
                        .padding(start = 20.dp, bottom = 5.dp)
                )
                Text(
                    text = stringResource(R.string.online_groc),
                    color = White,
                    modifier = Modifier.padding(start = 20.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                    letterSpacing = 5.5.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.gilroy_medium)),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
