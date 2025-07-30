package com.mashaal.ecommerce_app.ui.FilterScreen

import androidx.compose.foundation.background
import com.mashaal.ecommerce_app.ui.Common.clickableNoRipple
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.Common.CategoriesComponents.RoundedCheckbox
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.GeneralButton
import com.mashaal.ecommerce_app.ui.Common.LoadingState
import com.mashaal.ecommerce_app.ui.Common.ErrorState
import com.mashaal.ecommerce_app.ui.theme.AppIcons
import com.mashaal.ecommerce_app.ui.theme.appColors
import com.mashaal.ecommerce_app.ui.theme.appDimensions
import com.mashaal.ecommerce_app.ui.theme.appTextStyles

@Composable
fun FilterScreen(
    onBackClick: () -> Unit,
    onApplyFilter: (PriceRange?, Set<ProductPortion>) -> Unit,
    viewModel: FilterScreenViewModel = hiltViewModel()
) {
    val currentState by viewModel.state.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    when (val state = currentState) {
        is FilterScreenState.Loading -> {
            LoadingState()
        }
        is FilterScreenState.Error -> {
            ErrorState(error = state.message)
        }
        is FilterScreenState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.appColors.white)
                        .padding(
                            start = MaterialTheme.appDimensions.dimen24, 
                            end = MaterialTheme.appDimensions.dimen24, 
                            top = MaterialTheme.appDimensions.dimen32,
                            bottom = MaterialTheme.appDimensions.dimen24
                        )
                ) {
                    Icon(
                        imageVector = AppIcons.Close,
                        contentDescription = stringResource(R.string.close),
                        modifier = Modifier
                            .size(MaterialTheme.appDimensions.dimen24)
                            .align(Alignment.CenterStart)
                            .clickableNoRipple { onBackClick() },
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
                        .weight(1f)
                        .verticalScroll(scrollState)
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(
                                topStart = MaterialTheme.appDimensions.dimen16,
                                topEnd = MaterialTheme.appDimensions.dimen16
                            )
                        )
                        .background(MaterialTheme.appColors.bottomSheetBackgroundColor)
                        .padding(horizontal = MaterialTheme.appDimensions.dimen24)
                ) {
                    Text(
                        text = stringResource(R.string.price_range),
                        style = MaterialTheme.appTextStyles.sectionHeader(),
                        modifier = Modifier.padding(bottom = MaterialTheme.appDimensions.dimen16, top = MaterialTheme.appDimensions.dimen16)
                    )
                    
                    PriceRange.entries.forEach { priceRange ->
                        val isSelected = state.selectedPriceRange == priceRange
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = MaterialTheme.appDimensions.dimen8)
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
                            Spacer(modifier = Modifier.width(MaterialTheme.appDimensions.dimen16))
                            Text(
                                text = stringResource(priceRange.stringResId),
                                style = MaterialTheme.appTextStyles.productDetail(),
                                color = if (isSelected) MaterialTheme.appColors.primary else MaterialTheme.appColors.black
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen32))
                    
                    Text(
                        text = stringResource(R.string.product_portion),
                        style = MaterialTheme.appTextStyles.sectionHeader(),
                        modifier = Modifier.padding(bottom = MaterialTheme.appDimensions.dimen16)
                    )
                    
                    state.availableProductPortions.forEach { portion ->
                        val portionText = stringResource(portion.stringResId)
                        val isSelected = state.selectedProductPortions.contains(portion)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = MaterialTheme.appDimensions.dimen8)
                                .selectable(
                                    selected = isSelected,
                                    onClick = {
                                        viewModel.onEvent(FilterScreenEvent.OnProductPortionSelected(portion))
                                    }
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RoundedCheckbox(
                                checked = isSelected,
                                onCheckedChange = {
                                    viewModel.onEvent(FilterScreenEvent.OnProductPortionSelected(portion))
                                }
                            )
                            Spacer(modifier = Modifier.width(MaterialTheme.appDimensions.dimen16))
                            Text(
                                text = portionText,
                                style = MaterialTheme.appTextStyles.productDetail(),
                                color = if (isSelected) MaterialTheme.appColors.primary else MaterialTheme.appColors.black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen32))
                    GeneralButton(
                        onClick = {
                            onApplyFilter(state.selectedPriceRange, state.selectedProductPortions)
                        },
                        modifier = Modifier
                            .padding(
                                bottom = MaterialTheme.appDimensions.dimen32
                            ),
                        currentText = R.string.apply_filter,
                        containerColor = MaterialTheme.appColors.primary,
                        textColor = MaterialTheme.appColors.white,
                        enabled = state.selectedPriceRange != null || state.selectedProductPortions.isNotEmpty()
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.appDimensions.dimen32))
                }
            }
        }
    }
}