package com.mashaal.ecommerce_app.ui.MainScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mashaal.ecommerce_app.ui.theme.MainThemeColor

@Composable
fun SeeAllButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = "See all",
        color = MainThemeColor,
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.End,
        modifier = modifier
            .clickable { onClick() }
            .padding(vertical = 4.dp)
    )
}