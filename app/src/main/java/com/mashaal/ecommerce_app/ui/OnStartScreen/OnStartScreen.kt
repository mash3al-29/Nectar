package com.mashaal.ecommerce_app.ui.OnStartScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.theme.appColors
import com.mashaal.ecommerce_app.ui.theme.appDimensions
import com.mashaal.ecommerce_app.ui.theme.appShapes
import com.mashaal.ecommerce_app.ui.theme.appTextStyles

@Composable
fun OnStartScreen(onNavigateToMainScreen: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_onstart),
            contentDescription = stringResource(R.string.delivery_background_desc),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Scaffold(
            containerColor = MaterialTheme.appColors.transparent,
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(
                        horizontal = MaterialTheme.appDimensions.paddingMedium, 
                        vertical = MaterialTheme.appDimensions.paddingExtraLarge
                    ),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.carrot),
                    contentDescription = "Carrot Logo",
                    modifier = Modifier
                        .width(MaterialTheme.appDimensions.logoWidth)
                        .height(MaterialTheme.appDimensions.logoHeight)
                        .padding(bottom = MaterialTheme.appDimensions.paddingLarge),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = stringResource(R.string.welcome_text),
                    style = MaterialTheme.appTextStyles.welcomeTitle(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = MaterialTheme.appDimensions.spacingExtraSmall)
                )
                Text(
                    text = stringResource(R.string.welcome_subtext),
                    style = MaterialTheme.appTextStyles.welcomeSubtitle(),
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {
                        onNavigateToMainScreen()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.appColors.primary
                    ),
                    shape = MaterialTheme.appShapes.welcomeButton,
                    modifier = Modifier
                        .width(MaterialTheme.appDimensions.welcomeButtonWidth)
                        .height(MaterialTheme.appDimensions.welcomeButtonHeight)
                        .padding(top = MaterialTheme.appDimensions.paddingLarge)
                ) {
                    Text(
                        text = stringResource(R.string.welcome_button_text),
                        style = MaterialTheme.appTextStyles.welcomeButtonText(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
