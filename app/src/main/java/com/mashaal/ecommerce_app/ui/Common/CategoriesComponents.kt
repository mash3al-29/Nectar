package com.mashaal.ecommerce_app.ui.Common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.theme.AppIcons
import com.mashaal.ecommerce_app.ui.theme.appColors
import com.mashaal.ecommerce_app.ui.theme.appDimensions
import com.mashaal.ecommerce_app.ui.theme.appShapes
import com.mashaal.ecommerce_app.ui.theme.appTextStyles

object CategoriesComponents {
@Composable
fun CategoryItem(
    category: String,
    backgroundColor: Color,
    borderColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(width = MaterialTheme.appDimensions.dimen220, height = MaterialTheme.appDimensions.dimen170)
            .clip(MaterialTheme.appShapes.card)
            .background(backgroundColor)
            .border(MaterialTheme.appDimensions.dimen1, borderColor, MaterialTheme.appShapes.card)
            .clickableNoRipple(onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = getCategoryImageResource(category)),
                contentDescription = category,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(width = MaterialTheme.appDimensions.dimen111, height = MaterialTheme.appDimensions.dimen75)
                    .padding(bottom = MaterialTheme.appDimensions.dimen16)
            )
            Text(
                text = category,
                style = MaterialTheme.appTextStyles.productName(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.appColors.black,
                modifier = Modifier
                    .width(MaterialTheme.appDimensions.dimen93)
                    .height(MaterialTheme.appDimensions.dimen44)
            )
        }
    }
}


@Composable
fun getCategoryColors(category: String): Pair<Color, Color> {
    val colors = MaterialTheme.appColors
    return when (category) {
        "Fresh Fruits & Vegetable" -> Pair(
            colors.fruitsCardColor,
            colors.fruitsBorderColor
        )
        "Cooking Oil & Ghee" -> Pair(
            colors.cookingOilCardColor,
            colors.cookingOilBorderColor
        )
        "Meat & Fish" -> Pair(
            colors.meatCardColor,
            colors.meatBorderColor
        )
        "Bakery & Snacks" -> Pair(
            colors.bakeryCardColor,
            colors.bakeryBorderColor
        )
        "Dairy & Eggs" -> Pair(
            colors.dairyCardColor,
            colors.dairyBorderColor
        )
        "Beverages" -> Pair(
            colors.beveragesCardColor,
            colors.beveragesBorderColor
        )
        else -> Pair(
            colors.unknownCategoryCardColor,
            colors.unknownCategoryBorderColor
        )
    }
}

private fun getCategoryImageResource(category: String): Int {
    return when (category) {
        "Fresh Fruits & Vegetable" -> R.drawable.category1
        "Cooking Oil & Ghee" -> R.drawable.category2
        "Meat & Fish" -> R.drawable.category3
        "Bakery & Snacks" -> R.drawable.category4
        "Dairy & Eggs" -> R.drawable.category5
        "Beverages" -> R.drawable.category6
        else -> R.drawable.category1
    }
}

    @Composable
    fun RoundedCheckbox(
        checked: Boolean,
        onCheckedChange: (Boolean) -> Unit,
        modifier: Modifier = Modifier,
        checkedColor: Color = MaterialTheme.appColors.primary,
        borderColor: Color = MaterialTheme.appColors.gray,
        size: Dp = MaterialTheme.appDimensions.dimen24
    ) {
        Box(
            modifier = modifier
                .size(size)
                .clip(MaterialTheme.appShapes.checkbox)
                .background(if (checked) checkedColor else MaterialTheme.appColors.transparent)
                .border(
                    width = MaterialTheme.appDimensions.dimen2,
                    color = if (checked) checkedColor else borderColor,
                    shape = MaterialTheme.appShapes.checkbox
                )
                .clickableNoRipple { onCheckedChange(!checked) },
            contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Icon(
                    imageVector = AppIcons.Check,
                    contentDescription = stringResource(R.string.checked),
                    tint = MaterialTheme.appColors.white,
                    modifier = Modifier.size(size * 0.6f)
                )
            }
        }
    }

}