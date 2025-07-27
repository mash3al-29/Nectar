package com.mashaal.ecommerce_app.ui.Common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.theme.ErrorColor
import com.mashaal.ecommerce_app.ui.theme.GilroyMediumFont

@Composable
fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = colorResource(R.color.main_theme_color))
    }
}

@Composable
fun ErrorState(
    error: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(
                R.string.error_prefix,
                error ?: stringResource(R.string.unknown_error)
            ),
            color = ErrorColor,
            style = TextStyle(fontFamily = GilroyMediumFont, fontSize = 16.sp)
        )
    }
}

@Composable
fun EmptyState(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = com.mashaal.ecommerce_app.ui.theme.Gray
        )
    }
} 