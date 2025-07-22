package com.mashaal.ecommerce_app.ui.ProductScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mashaal.ecommerce_app.ui.theme.GilroyBoldFont
import com.mashaal.ecommerce_app.ui.theme.MainThemeColor

@Composable
fun AddToBasketButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MainThemeColor),
        shape = RoundedCornerShape(16.dp),
    ) {
        Text(
            text = "Add To Basket",
            fontFamily = GilroyBoldFont,
            fontSize = 18.sp
        )
    }
}