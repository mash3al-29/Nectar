package com.mashaal.ecommerce_app.ui.SplashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.OnStartScreen.OnStartScreenViewModel
import com.mashaal.ecommerce_app.ui.theme.appColors
import com.mashaal.ecommerce_app.ui.theme.appDimensions
import com.mashaal.ecommerce_app.ui.theme.appTextStyles
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToHomeFromSplash: () -> Unit,
    onNavigateToMainFromSplash: () -> Unit = {},
    viewModel: OnStartScreenViewModel = hiltViewModel()
) {
    val firstTimeState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        delay(2500)
        
        val stateAtNavigation = firstTimeState
        when (stateAtNavigation) {
            is com.mashaal.ecommerce_app.ui.OnStartScreen.OnStartScreenState.Success -> {
                if (stateAtNavigation.shouldShowOnStartScreen) {
                    onNavigateToHomeFromSplash()
                } else if (!stateAtNavigation.onboardingJustCompleted) {
                    onNavigateToMainFromSplash()
                }
            }
            else -> {
                onNavigateToHomeFromSplash()
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.appColors.primary,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.carrot),
                    contentDescription = stringResource(R.string.carrot_logo_desc),
                    modifier = Modifier
                        .width(MaterialTheme.appDimensions.dimen60)
                        .height(MaterialTheme.appDimensions.dimen60)
                )

                Column {
                    Image(
                        painter = painterResource(id = R.drawable.nectar_logo),
                        contentDescription = stringResource(R.string.nectar_logo_desc),
                        modifier = Modifier
                            .width(MaterialTheme.appDimensions.dimen200)
                            .height(MaterialTheme.appDimensions.dimen50)
                            .padding(start = MaterialTheme.appDimensions.dimen20, bottom = MaterialTheme.appDimensions.dimen4)
                    )
                    Text(
                        text = stringResource(R.string.online_groc),
                        style = MaterialTheme.appTextStyles.splashScreenText(),
                        modifier = Modifier.padding(start = MaterialTheme.appDimensions.dimen20),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
