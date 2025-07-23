package com.mashaal.ecommerce_app.ui.CategorySelectedScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.theme.BottomSheetBackgroundColor
import com.mashaal.ecommerce_app.ui.theme.GilroyMediumFont
import com.mashaal.ecommerce_app.ui.theme.MainThemeColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    onApplyFilter: () -> Unit,
    productPortions: List<String>,
    selectedPriceRange: String?,
    selectedProductPortions: Set<String>,
    onPriceRangeSelected: (String?) -> Unit,
    onProductDetailSelected: (String, Boolean) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BottomSheetBackgroundColor, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .padding(24.dp)
            .verticalScroll(scrollState),
    ) {
        Text(
            text = stringResource(R.string.filters),
            fontFamily = GilroyMediumFont,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.price_range),
            fontFamily = GilroyMediumFont,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
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
            val isSelected = selectedPriceRange == priceRange
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            val newPriceRange = if (isSelected) null else priceRange
                            onPriceRangeSelected(newPriceRange)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundedCheckbox(
                    checked = isSelected,
                    onCheckedChange = { checked ->
                        val newPriceRange = if (checked) priceRange else null
                        onPriceRangeSelected(newPriceRange)
                    }
                )

                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = priceRange,
                    fontFamily = GilroyMediumFont,
                    fontSize = 16.sp,
                    color = if (isSelected) MainThemeColor else Color.Black,
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
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        productPortions.forEach { detail ->
            val isSelected = selectedProductPortions.contains(detail)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            val newSelected = !isSelected
                            onProductDetailSelected(detail, newSelected)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoundedCheckbox(
                    checked = isSelected,
                    onCheckedChange = { checked ->
                        onProductDetailSelected(detail, checked)
                    }
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = detail,
                    fontFamily = GilroyMediumFont,
                    fontSize = 16.sp,
                    color = if (isSelected) MainThemeColor else Color.Black,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                onApplyFilter()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MainThemeColor)
        ) {
            Text(
                text = stringResource(R.string.apply_filter),
                fontFamily = GilroyMediumFont,
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun RoundedCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    checkedColor: Color = MainThemeColor,
    borderColor: Color = Color.Gray,
    cornerRadius: Dp = 8.dp,
    size: Dp = 24.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(cornerRadius))
            .background(if (checked) checkedColor else Color.Transparent)
            .border(
                width = 2.dp,
                color = if (checked) checkedColor else borderColor,
                shape = RoundedCornerShape(cornerRadius)
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Checked",
                tint = Color.White,
                modifier = Modifier.size(size * 0.6f)
            )
        }
    }
}


