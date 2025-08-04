package com.mashaal.ecommerce_app

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mashaal.ecommerce_app.fakeimpls.fake_daos.FakeProductDaoUI
import com.mashaal.ecommerce_app.ui.CategorySelectedScreen.CategorySelectedScreen
import com.mashaal.ecommerce_app.ui.FilterScreen.PriceRange
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CategorySelectedScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    lateinit var fakeProductDao: FakeProductDaoUI

    @Before
    fun setup() {
        hiltRule.inject()

        runBlocking {
            fakeProductDao.insertProduct(TestUtils.testProduct(id = 1, name = "Apple", price = 5.0, category = "Fruits"))
            fakeProductDao.insertProduct(TestUtils.testProduct(id = 2, name = "Banana", price = 3.0, category = "Fruits"))
        }
    }

    @Test
    fun categoryScreen_displaysProductsCorrectly() {
        composeTestRule.setContent {
            CategorySelectedScreen(
                categoryName = "Fruits",
                onBackClick = {},
                onProductClick = {},
                navigateToFilter = {},
                filterResults = null
            )
        }

        composeTestRule.waitUntil(
            condition = { composeTestRule.onAllNodesWithText("Apple").fetchSemanticsNodes().isNotEmpty() },
            timeoutMillis = 5_000
        )

        composeTestRule.onNodeWithText("Apple").assertExists()
        composeTestRule.onNodeWithText("Banana").assertExists()
    }

    @Test
    fun categoryScreen_showsEmptyStateWhenNoResults() {
        composeTestRule.setContent {
            CategorySelectedScreen(
                categoryName = "Vegetables",
                onBackClick = {},
                onProductClick = {},
                navigateToFilter = {},
                filterResults = null
            )
        }

        // Should show empty state after loading
        composeTestRule.waitUntil(
            condition = { composeTestRule.onAllNodesWithText("No products found for Vegetables").fetchSemanticsNodes().isNotEmpty() },
            timeoutMillis = 5_000
        )

        composeTestRule.onNodeWithText("No products found for Vegetables").assertExists()
    }

    @Test
    fun categoryScreen_showsEmptyStateWhenFiltersAppliedButNoResults() {
        // Insert a product that won't match the filter
        runBlocking {
            fakeProductDao.insertProduct(TestUtils.testProduct(id = 3, name = "Carrot", price = 1.0, category = "Vegetables"))
        }
        
        composeTestRule.setContent {
            CategorySelectedScreen(
                categoryName = "Vegetables",
                onBackClick = {},
                onProductClick = {},
                navigateToFilter = {},
                filterResults = Pair(PriceRange.RANGE_8_TO_10, setOf())
            )
        }

        // Should show empty state for filters
        composeTestRule.waitUntil(
            condition = { composeTestRule.onAllNodesWithText("No products match the selected filters").fetchSemanticsNodes().isNotEmpty() },
            timeoutMillis = 5_000
        )

        composeTestRule.onNodeWithText("No products match the selected filters").assertExists()
    }

    @Test
    fun addToCartButton_addsProductCorrectly() {
        composeTestRule.setContent {
            CategorySelectedScreen(
                categoryName = "Fruits",
                onBackClick = {},
                onProductClick = {},
                navigateToFilter = {},
                filterResults = null
            )
        }

        // Wait for Apple product
        composeTestRule.waitUntil(
            condition = { composeTestRule.onAllNodesWithText("Apple").fetchSemanticsNodes().isNotEmpty() },
            timeoutMillis = 5_000
        )

        // Click Add to Cart on Apple
        composeTestRule.onAllNodesWithContentDescription("Add To Basket").onFirst().performClick()

        // Button should change to "Added!" icon (check mark)
        composeTestRule.onAllNodesWithContentDescription("Added!").onFirst().assertExists()
    }

    @Test
    fun navigateToFilterButton_invokesCallback() {
        var filterClicked = false

        composeTestRule.setContent {
            CategorySelectedScreen(
                categoryName = "Fruits",
                onBackClick = {},
                onProductClick = {},
                navigateToFilter = { filterClicked = true },
                filterResults = null
            )
        }

        composeTestRule.onNodeWithContentDescription("Filter").performClick()

        assert(filterClicked)
    }

    @Test
    fun backButton_invokesCallback() {
        var backClicked = false

        composeTestRule.setContent {
            CategorySelectedScreen(
                categoryName = "Fruits",
                onBackClick = { backClicked = true },
                onProductClick = {},
                navigateToFilter = {},
                filterResults = null
            )
        }

        composeTestRule.onNodeWithContentDescription("Back").performClick()

        assert(backClicked)
    }
}
