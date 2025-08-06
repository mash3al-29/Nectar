package com.mashaal.ecommerce_app.ui.MainScreen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mashaal.ecommerce_app.ui.Common.SeeAllButton
import com.mashaal.ecommerce_app.ui.theme.appDimensions
import com.mashaal.ecommerce_app.ui.theme.appTextStyles

@Composable
fun HeaderRow(@StringRes mainText: Int, onClickHandler: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = MaterialTheme.appDimensions.dimen24,
                end = MaterialTheme.appDimensions.dimen24,
                top = MaterialTheme.appDimensions.dimen32,
                bottom = MaterialTheme.appDimensions.dimen16
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(mainText),
            style = MaterialTheme.appTextStyles.sectionHeader()
        )
        SeeAllButton(
            onClick = onClickHandler
        )
    }
}