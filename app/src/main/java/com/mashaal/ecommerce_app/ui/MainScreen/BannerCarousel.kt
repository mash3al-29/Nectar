package com.mashaal.ecommerce_app.ui.MainScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.mashaal.ecommerce_app.R
import com.mashaal.ecommerce_app.ui.theme.appColors
import com.mashaal.ecommerce_app.ui.theme.appDimensions
import com.mashaal.ecommerce_app.ui.theme.appShapes
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BannerCarousel(
    modifier: Modifier = Modifier,
    autoScrollDuration: Long = 3000
) {
    val bannerImages = listOf(
        R.drawable.img_banner1,
        R.drawable.img_banner2,
        R.drawable.img_banner3
    )
    val pagerState = rememberPagerState(pageCount = { bannerImages.size })
    LaunchedEffect(key1 = pagerState) {
        while (true) {
            delay(autoScrollDuration)
            val nextPage = (pagerState.currentPage + 1) % bannerImages.size
            pagerState.animateScrollToPage(nextPage)
        }
    }
    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.appDimensions.bannerHeight)
                .clip(MaterialTheme.appShapes.card)
        ) { page ->
            Image(
                painter = painterResource(id = bannerImages[page]),
                contentDescription = stringResource(R.string.banner_image, page + 1),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = MaterialTheme.appDimensions.paddingMedium),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.appDimensions.spacingSmall)
        ) {
            repeat(bannerImages.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .size(
                            width = if (isSelected) MaterialTheme.appDimensions.indicatorWidthLarge else MaterialTheme.appDimensions.indicatorWidthSmall, 
                            height = MaterialTheme.appDimensions.indicatorHeight
                        )
                        .clip(CircleShape)
                        .background(
                            if (isSelected) MaterialTheme.appColors.primary
                            else MaterialTheme.appColors.gray.copy(alpha = 0.5f)
                        )
                )
            }
        }
    }
}