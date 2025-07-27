package com.mashaal.ecommerce_app.ui.MainScreen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mashaal.ecommerce_app.ui.Common.SeeAllButton
import com.mashaal.ecommerce_app.ui.theme.*

@Composable
fun HeaderRow(@StringRes mainText: Int, onClickHandler: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 24.dp,
                bottom = 8.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(mainText),
            style = AppTextStyles.SectionHeader
        )
        SeeAllButton(
            onClick = onClickHandler
        )
    }
}