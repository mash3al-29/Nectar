package com.mashaal.ecommerce_app.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.mashaal.ecommerce_app.R

class AppColors {
    @get:Composable
    val primary: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.primary))
    
    @get:Composable 
    val checkoutButtonColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.checkout_button_color))
    
    @get:Composable
    val errorColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.error_color))
    
    @get:Composable
    val white: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.white))
    
    @get:Composable
    val black: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.black))
    
    @get:Composable
    val transparent: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.transparent))
    
    @get:Composable
    val gray: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.gray))
    
    @get:Composable
    val lightGray: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.light_gray))
    
    @get:Composable
    val darkGray: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.dark_gray))
    
    @get:Composable
    val cardBackground: Color get() = white
    
    @get:Composable
    val searchBackgroundColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.search_background_color))

    @get:Composable
    val bottomSheetBackgroundColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.bottom_sheet_background_color))

    @get:Composable
    val searchTextColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.search_text_color))
    
    @get:Composable
    val productDetailColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.product_detail_color))
    
    @get:Composable
    val locationIconColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.location_icon_color))
    
    @get:Composable
    val locationTextColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.location_text_color))
    
    @get:Composable
    val dividerColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.divider_color))
    
    @get:Composable
    val cartDividerColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.cart_divider_color))
    
    @get:Composable
    val starColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.star_color))
    
    @get:Composable
    val removeIconColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.remove_icon_color))
    
    @get:Composable
    val gradientTop: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.gradient_top))
    
    @get:Composable
    val gradientMiddle: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.gradient_middle))
    
    @get:Composable
    val gradientBottom: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.gradient_bottom))
    
    @get:Composable
    val fruitsCardColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.fruits_card_color))
    
    @get:Composable
    val fruitsBorderColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.fruits_border_color))
    
    @get:Composable
    val cookingOilCardColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.cooking_oil_card_color))
    
    @get:Composable
    val cookingOilBorderColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.cooking_oil_border_color))
    
    @get:Composable
    val meatCardColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.meat_card_color))
    
    @get:Composable
    val meatBorderColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.meat_border_color))
    
    @get:Composable
    val bakeryCardColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.bakery_card_color))
    
    @get:Composable
    val bakeryBorderColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.bakery_border_color))
    
    @get:Composable
    val dairyCardColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.dairy_card_color))
    
    @get:Composable
    val dairyBorderColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.dairy_border_color))
    
    @get:Composable
    val beveragesCardColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.beverages_card_color))
    
    @get:Composable
    val beveragesBorderColor: Color get() = Color(ContextCompat.getColor(LocalContext.current, R.color.beverages_border_color))
    
    @get:Composable
    val unknownCategoryCardColor: Color get() = beveragesCardColor
    
    @get:Composable
    val unknownCategoryBorderColor: Color get() = beveragesBorderColor
}