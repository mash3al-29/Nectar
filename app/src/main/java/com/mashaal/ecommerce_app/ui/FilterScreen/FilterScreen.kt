package com.mashaal.ecommerce_app.ui.FilterScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.Common.CategoriesComponents.RoundedCheckbox
import com.mashaal.ecommerce_app.ui.Common.ProductComponents.GeneralButton
import com.mashaal.ecommerce_app.ui.theme.AppIcons
import com.mashaal.ecommerce_app.ui.theme.Black
import com.mashaal.ecommerce_app.ui.theme.BottomSheetBackgroundColor
import com.mashaal.ecommerce_app.ui.theme.GilroyBoldFont
import com.mashaal.ecommerce_app.ui.theme.GilroyMediumFont
import com.mashaal.ecommerce_app.ui.theme.MainThemeColor
import com.mashaal.ecommerce_app.ui.theme.White

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
        containerColor = White
    ){ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(start = 8.dp, end = 8.dp, bottom = 32.dp)
            ) {
                Icon(
                    imageVector = AppIcons.Close,
                    contentDescription = "Close",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterStart)
                        .clickable { onBackClick() },
                    tint = Black
                )
                Text(
                    text = stringResource(R.string.filters),
                    fontFamily = GilroyBoldFont,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center),
                    color = Black
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(BottomSheetBackgroundColor)
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = stringResource(R.string.price_range),
                    fontFamily = GilroyMediumFont,
                    fontSize = 18.sp,
                    color = Black,
                    modifier = Modifier.padding(vertical = 24.dp, horizontal = 24.dp)
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
                            .padding(vertical = 10.dp, horizontal = 24.dp)
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

                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = priceRange,
                            fontFamily = GilroyMediumFont,
                            fontSize = 16.sp,
                            color = if (isSelected) MainThemeColor else Black,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text = stringResource(R.string.product_portion),
                    fontFamily = GilroyMediumFont,
                    fontSize = 18.sp,
                    color = Black,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 24.dp)
                )

                state.productPortions.forEach { detail ->
                    val isSelected = state.selectedProductPortions.contains(detail)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 24.dp)
                            .selectable(
                                selected = isSelected,
                                onClick = {
                                    val newSelected = !isSelected
                                    viewModel.onEvent(FilterScreenEvent.OnProductPortionSelected(detail, newSelected))
                                }
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RoundedCheckbox(
                            checked = isSelected,
                            onCheckedChange = { checked ->
                                viewModel.onEvent(FilterScreenEvent.OnProductPortionSelected(detail, checked))
                            }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = detail,
                            fontFamily = GilroyMediumFont,
                            fontSize = 16.sp,
                            color = if (isSelected) MainThemeColor else Black,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Spacer(modifier = Modifier.height(32.dp))
                GeneralButton(
                    onClick = {
                        onApplyFilter(state.selectedPriceRange, state.selectedProductPortions)
                    },
                    currentText = R.string.apply_filter,
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
                )
            }
        }
    }
}