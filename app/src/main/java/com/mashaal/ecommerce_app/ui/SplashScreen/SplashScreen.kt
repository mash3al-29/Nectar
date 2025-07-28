package com.mashaal.ecommerce_app.ui.SplashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.theme.appColors
import com.mashaal.ecommerce_app.ui.theme.appDimensions
import com.mashaal.ecommerce_app.ui.theme.appTextStyles
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
        containerColor = MaterialTheme.appColors.primary,
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
                    contentDescription = stringResource(R.string.carrot_logo_desc),
                    modifier = Modifier
                        .width(MaterialTheme.appDimensions.splashScreenCarrotWidth)
                        .height(MaterialTheme.appDimensions.splashScreenCarrotHeight)
                )

            Column {
                Image(
                    painter = painterResource(id = R.drawable.nectar_logo),
                    contentDescription = stringResource(R.string.nectar_logo_desc),
                    modifier = Modifier
                        .width(MaterialTheme.appDimensions.splashScreenNectarWidth)
                        .height(MaterialTheme.appDimensions.splashScreenNectarHeight)
                        .padding(start = MaterialTheme.appDimensions.paddingMediumInBetween, bottom = MaterialTheme.appDimensions.paddingExtraSmall)
                )
                Text(
                    text = stringResource(R.string.online_groc),
                    style = MaterialTheme.appTextStyles.splashScreenText(),
                    modifier = Modifier.padding(start = MaterialTheme.appDimensions.paddingMediumInBetween),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
