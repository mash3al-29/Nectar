package com.mashaal.ecommerce_app.ui.Common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.theme.*


@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController

) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(MaterialTheme.appDimensions.searchBarHeight)
            .clip(MaterialTheme.appShapes.searchBar)
            .background(MaterialTheme.appColors.searchBackgroundColor)
            .padding(horizontal = MaterialTheme.appDimensions.paddingMedium),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = AppIcons.Search,
                contentDescription = stringResource(R.string.search_store),
                tint = MaterialTheme.appColors.black,
                modifier = Modifier.size(MaterialTheme.appDimensions.iconSizeSmall)
            )
            Spacer(modifier = Modifier.width(MaterialTheme.appDimensions.spacingSmall))
            Box(modifier = Modifier.weight(1f)) {
                if (query.isEmpty()) {
                    Text(
                        text = stringResource(R.string.search_store),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.appColors.searchTextColor
                    )
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            focusManager.clearFocus()
                            keyboardController.hide()
                        }
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search,
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
        shape = MaterialTheme.appShapes.bottomNav,
        color = MaterialTheme.appColors.white,
        tonalElevation = MaterialTheme.appDimensions.elevationLarge,
        shadowElevation = MaterialTheme.appDimensions.elevationLarge
    ) {
        NavigationBar(
            containerColor = MaterialTheme.appColors.transparent,
            tonalElevation = 0.dp,
            modifier = Modifier.padding(vertical = MaterialTheme.appDimensions.spacingSmall)
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
                            style = MaterialTheme.appTextStyles.navigationLabel()
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
                        selectedIconColor = MaterialTheme.appColors.primary,
        selectedTextColor = MaterialTheme.appColors.primary,
                        indicatorColor = MaterialTheme.appColors.white,
                        unselectedIconColor = MaterialTheme.appColors.black,
                        unselectedTextColor = MaterialTheme.appColors.black
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
        style = MaterialTheme.appTextStyles.seeAllLink(),
        textAlign = TextAlign.End,
        modifier = modifier
            .clickable { onClick() }
            .padding(vertical = MaterialTheme.appDimensions.spacingExtraSmall)
    )
}