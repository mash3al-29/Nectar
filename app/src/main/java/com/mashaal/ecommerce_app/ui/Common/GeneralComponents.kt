package com.mashaal.ecommerce_app.ui.Common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.theme.*


@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(SearchBackgroundColor)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = AppIcons.Search,
                contentDescription = stringResource(R.string.search_store),
                tint = Black,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(modifier = Modifier.weight(1f)) {
                if (query.isEmpty()) {
                    Text(
                        text = stringResource(R.string.search_store),
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp, letterSpacing = 0.sp),
                        color = SearchTextColor
                    )
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search
                    ),
                )
            }
        }
    }
}


@Composable
fun BottomNavBar(
    onShopClick: () -> Unit,
    onExploreClick: () -> Unit,
    onCartClick: () -> Unit,
    selectedIndex: Int = 0
) {
    val items = listOf(
        BottomNavItem.SHOP,
        BottomNavItem.EXPLORE,
        BottomNavItem.CART
    )
    var selectedItem by remember { mutableIntStateOf(selectedIndex) }
    LaunchedEffect(selectedIndex) {
        selectedItem = selectedIndex
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        color = White,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = stringResource(item.titleResId)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(item.titleResId),
                            fontFamily = GilroyMediumFont,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        when (index) {
                            0 -> onShopClick()
                            1 -> onExploreClick()
                            2 -> onCartClick()
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MainThemeColor,
                        selectedTextColor = MainThemeColor,
                        indicatorColor = White,
                        unselectedIconColor = Black,
                        unselectedTextColor = Black
                    )
                )
            }
        }
    }
}



enum class BottomNavItem(val titleResId: Int, val icon: Int) {
    SHOP(R.string.nav_shop, R.drawable.ic_shop),
    EXPLORE(R.string.nav_explore, R.drawable.ic_explore),
    CART(R.string.nav_cart, R.drawable.ic_cart)
}

@Composable
fun SeeAllButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.see_all),
        style = AppTextStyles.SeeAllLink,
        textAlign = TextAlign.End,
        modifier = modifier
            .clickable { onClick() }
            .padding(vertical = 4.dp)
    )
}