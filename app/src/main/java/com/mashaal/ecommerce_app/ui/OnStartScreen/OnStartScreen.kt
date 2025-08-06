package com.mashaal.ecommerce_app.ui.OnStartScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.Common.LoadingState
import com.mashaal.ecommerce_app.ui.Common.ErrorState
import com.mashaal.ecommerce_app.ui.theme.appColors
import com.mashaal.ecommerce_app.ui.theme.appDimensions
import com.mashaal.ecommerce_app.ui.theme.appShapes
import com.mashaal.ecommerce_app.ui.theme.appTextStyles

@Composable
fun OnStartScreen(
    onNavigateToMainScreen: () -> Unit,
    viewModel: OnStartScreenViewModel = hiltViewModel()
) {
    val currentState by viewModel.state.collectAsStateWithLifecycle()

    when (val state = currentState) {
        is OnStartScreenState.Loading -> {
            LoadingState()
        }
        is OnStartScreenState.Error -> {
            ErrorState(error = state.message)
        }
        is OnStartScreenState.Success -> {
            if (state.shouldShowOnStartScreen) {
                OnStartContent(
                    onGetStartedClick = {
                        viewModel.onEvent(OnStartScreenEvent.OnGetStartedClicked)
                        onNavigateToMainScreen()
                    }
                )
            }
        }
    }
}

@Composable
private fun OnStartContent(onGetStartedClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
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
                        horizontal = MaterialTheme.appDimensions.dimen16,
                        vertical = MaterialTheme.appDimensions.dimen32
                    ),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.carrot),
                    contentDescription = stringResource(R.string.carrot_logo_desc),
                    modifier = Modifier
                        .width(MaterialTheme.appDimensions.dimen100)
                        .height(MaterialTheme.appDimensions.dimen100)
                        .padding(bottom = MaterialTheme.appDimensions.dimen24),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = stringResource(R.string.welcome_text),
                    style = MaterialTheme.appTextStyles.welcomeTitle(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = MaterialTheme.appDimensions.dimen4)
                )
                Text(
                    text = stringResource(R.string.welcome_subtext),
                    style = MaterialTheme.appTextStyles.welcomeSubtitle(),
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = onGetStartedClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.appColors.primary
                    ),
                    shape = MaterialTheme.appShapes.welcomeButton,
                    modifier = Modifier
                        .width(MaterialTheme.appDimensions.dimen300)
                        .height(MaterialTheme.appDimensions.dimen90)
                        .padding(top = MaterialTheme.appDimensions.dimen24)
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
