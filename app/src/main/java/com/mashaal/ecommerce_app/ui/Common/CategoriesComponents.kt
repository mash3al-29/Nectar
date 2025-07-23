package com.mashaal.ecommerce_app.ui.Common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.theme.GilroyBoldFont

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
            Color.hsl(142f, 0.38f, 0.51f, 0.1f),
            Color.hsl(142f, 0.38f, 0.51f, 0.7f)
        )
        "Cooking Oil & Ghee" -> Pair(
            Color.hsl(31f, 0.92f, 0.64f, 0.1f),
            Color.hsl(31f, 0.92f, 0.64f, 0.7f)
        )
        "Meat & Fish" -> Pair(
            Color.hsl(11f, 0.86f, 0.77f, 0.25f),
            Color.hsl(11f, 0.86f, 0.77f, 1f)
        )
        "Bakery & Snacks" -> Pair(
            Color.hsl(284f, 0.44f, 0.78f, 0.25f),
            Color.hsl(284f, 0.44f, 0.78f, 1f)
        )
        "Dairy & Eggs" -> Pair(
            Color.hsl(46f, 0.96f, 0.79f, 0.25f),
            Color.hsl(46f, 0.96f, 0.79f, 1f)
        )
        "Beverages" -> Pair(
            Color.hsl(201f, 0.76f, 0.84f, 0.25f),
            Color.hsl(201f, 0.76f, 0.84f, 1f)
        )
        else -> Pair(
            Color.hsl(201f, 0.76f, 0.84f, 0.25f),
            Color.hsl(201f, 0.76f, 0.84f, 1f)
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

}