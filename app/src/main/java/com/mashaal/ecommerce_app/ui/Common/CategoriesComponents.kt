package com.mashaal.ecommerce_app.ui.Common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.theme.AppIcons
import com.mashaal.ecommerce_app.ui.theme.BakeryBorderColor
import com.mashaal.ecommerce_app.ui.theme.BakeryCardColor
import com.mashaal.ecommerce_app.ui.theme.BeveragesBorderColor
import com.mashaal.ecommerce_app.ui.theme.BeveragesCardColor
import com.mashaal.ecommerce_app.ui.theme.CookingOilBorderColor
import com.mashaal.ecommerce_app.ui.theme.CookingOilCardColor
import com.mashaal.ecommerce_app.ui.theme.DairyBorderColor
import com.mashaal.ecommerce_app.ui.theme.DairyCardColor
import com.mashaal.ecommerce_app.ui.theme.FruitsBorderColor
import com.mashaal.ecommerce_app.ui.theme.FruitsCardColor
import com.mashaal.ecommerce_app.ui.theme.GilroyBoldFont
import com.mashaal.ecommerce_app.ui.theme.MainThemeColor
import com.mashaal.ecommerce_app.ui.theme.MeatBorderColor
import com.mashaal.ecommerce_app.ui.theme.MeatCardColor
import com.mashaal.ecommerce_app.ui.theme.UnknownCategoryBorderColor
import com.mashaal.ecommerce_app.ui.theme.UnknownCategoryCardColor
import com.mashaal.ecommerce_app.ui.theme.White

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
            .size(width = 220.dp, height = 170.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(18.dp))
            .clickable(onClick = onClick),
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
                    .size(width = 111.dp, height = 75.dp)
                    .padding(bottom = 16.dp)
            )
            Text(
                text = category,
                fontFamily = GilroyBoldFont,
                fontSize = 16.sp,
                lineHeight = 22.sp,
                letterSpacing = 0.1.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF181B19),
                modifier = Modifier
                    .width(93.dp)
                    .height(44.dp)
            )
        }
    }
}


@Composable
fun getCategoryColors(category: String): Pair<Color, Color> {
    return when (category) {
        "Fresh Fruits & Vegetable" -> Pair(
            FruitsCardColor,
            FruitsBorderColor
        )
        "Cooking Oil & Ghee" -> Pair(
            CookingOilCardColor,
            CookingOilBorderColor
        )
        "Meat & Fish" -> Pair(
            MeatCardColor,
            MeatBorderColor
        )
        "Bakery & Snacks" -> Pair(
            BakeryCardColor,
            BakeryBorderColor
        )
        "Dairy & Eggs" -> Pair(
            DairyCardColor,
            DairyBorderColor
        )
        "Beverages" -> Pair(
            BeveragesCardColor,
            BeveragesBorderColor
        )
        else -> Pair(
            UnknownCategoryCardColor,
            UnknownCategoryBorderColor
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
                    imageVector = AppIcons.Check,
                    contentDescription = "Checked",
                    tint = White,
                    modifier = Modifier.size(size * 0.6f)
                )
            }
        }
    }

}