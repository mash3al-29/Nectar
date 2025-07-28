package com.mashaal.ecommerce_app.ui.FilterScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.Common.CategoriesComponents.RoundedCheckbox
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.GeneralButton
import com.mashaal.ecommerce_app.ui.Common.LoadingState
import com.mashaal.ecommerce_app.ui.Common.ErrorState
import com.mashaal.ecommerce_app.ui.theme.AppIcons
import com.mashaal.ecommerce_app.ui.theme.appColors
import com.mashaal.ecommerce_app.ui.theme.appDimensions
import com.mashaal.ecommerce_app.ui.theme.appShapes
import com.mashaal.ecommerce_app.ui.theme.appTextStyles

@Composable
fun FilterScreen(
    onBackClick: () -> Unit,
    onApplyFilter: (String?, Set<String>) -> Unit,
    viewModel: FilterScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.appColors.white
    ){ paddingValues ->
        if (state.isLoading) {
            LoadingState()
        } else if (state.error != null) {
            ErrorState(error = state.error)
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.appColors.white)
                    .padding(start = MaterialTheme.appDimensions.paddingSmall, end = MaterialTheme.appDimensions.paddingSmall, bottom = MaterialTheme.appDimensions.spacingLarge)
            ) {
                Icon(
                    imageVector = AppIcons.Close,
                    contentDescription = stringResource(R.string.close),
                    modifier = Modifier
                        .size(MaterialTheme.appDimensions.iconSizeMedium)
                        .align(Alignment.CenterStart)
                        .clickable { onBackClick() },
                    tint = MaterialTheme.appColors.black
                )
                Text(
                    text = stringResource(R.string.filters),
                    style = MaterialTheme.appTextStyles.screenTitle(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.appShapes.card)
                    .background(MaterialTheme.appColors.bottomSheetBackgroundColor)
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = stringResource(R.string.price_range),
                    style = MaterialTheme.appTextStyles.sectionHeader(),
                    modifier = Modifier.padding(vertical = MaterialTheme.appDimensions.paddingLarge, horizontal = MaterialTheme.appDimensions.paddingLarge)
                )
                val priceRanges = listOf(
                    stringResource(R.string.price_under_2),
                    stringResource(R.string.price_2_to_4),
                    stringResource(R.string.price_4_to_6),
                    stringResource(R.string.price_6_to_8),
                    stringResource(R.string.price_8_to_10),
                    stringResource(R.string.price_10_to_12),
                    stringResource(R.string.price_12_to_15),
                    stringResource(R.string.price_15_to_20)
                )
                priceRanges.forEach { priceRange ->
                    val isSelected = state.selectedPriceRange == priceRange
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = MaterialTheme.appDimensions.paddingMedium, start = MaterialTheme.appDimensions.paddingMedium, end = MaterialTheme.appDimensions.paddingMedium)
                            .selectable(
                                selected = isSelected,
                                onClick = {
                                    val newPriceRange = if (isSelected) null else priceRange
                                    viewModel.onEvent(FilterScreenEvent.OnPriceRangeSelected(newPriceRange))
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RoundedCheckbox(
                            checked = isSelected,
                            onCheckedChange = { checked ->
                                val newPriceRange = if (checked) priceRange else null
                                viewModel.onEvent(FilterScreenEvent.OnPriceRangeSelected(newPriceRange))
                            }
                        )
                        Spacer(modifier = Modifier.width(MaterialTheme.appDimensions.spacingSmall))
                        Text(
                            text = priceRange,
                            style = MaterialTheme.appTextStyles.productDetail(),
                            color = if (isSelected) MaterialTheme.appColors.primary else MaterialTheme.appColors.black,
                            modifier = Modifier.padding(start = MaterialTheme.appDimensions.spacingSmall)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.spacingMedium))
                Text(
                    text = stringResource(R.string.product_portion),
                    style = MaterialTheme.appTextStyles.sectionHeader(),
                    modifier = Modifier.padding(vertical = MaterialTheme.appDimensions.paddingMedium, horizontal = MaterialTheme.appDimensions.paddingLarge)
                )
                state.productPortionsResIds.forEach { resId ->
                    val portionText = stringResource(resId)
                    val isSelected = state.selectedProductPortions.contains(portionText)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = MaterialTheme.appDimensions.paddingMedium, start = MaterialTheme.appDimensions.paddingMedium, end = MaterialTheme.appDimensions.paddingMedium)
                            .selectable(
                                selected = isSelected,
                                onClick = {
                                    val newSelected = !isSelected
                                    viewModel.onEvent(FilterScreenEvent.OnProductPortionSelected(portionText, newSelected))
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RoundedCheckbox(
                            checked = isSelected,
                            onCheckedChange = { checked ->
                                viewModel.onEvent(FilterScreenEvent.OnProductPortionSelected(portionText, checked))
                            }
                        )
                        Spacer(modifier = Modifier.width(MaterialTheme.appDimensions.spacingSmall))
                        Text(
                            text = portionText,
                            style = MaterialTheme.appTextStyles.productDetail(),
                            color = if (isSelected) MaterialTheme.appColors.primary else MaterialTheme.appColors.black,
                            modifier = Modifier.padding(start = MaterialTheme.appDimensions.spacingSmall)
                        )
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.spacingSmall))
                }
                Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.spacingLarge))
                GeneralButton(
                    onClick = {
                        onApplyFilter(state.selectedPriceRange, state.selectedProductPortions)
                    },
                    currentText = R.string.apply_filter,
                    modifier = Modifier.padding(start = MaterialTheme.appDimensions.paddingMedium, end = MaterialTheme.appDimensions.paddingMedium, bottom = MaterialTheme.appDimensions.paddingMedium),
                    containerColor = MaterialTheme.appColors.primary,
                    textColor = MaterialTheme.appColors.white
                )
            }
        }
    }
}
}