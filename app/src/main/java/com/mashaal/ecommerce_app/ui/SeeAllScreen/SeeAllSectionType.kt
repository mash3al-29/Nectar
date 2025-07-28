package com.mashaal.ecommerce_app.ui.SeeAllScreen

import androidx.annotation.StringRes
import com.mashaal.ecommerce_app.R

enum class SeeAllSectionType(
    @StringRes val titleResId: Int,
    @StringRes val emptyStateResId: Int
) {
    EXCLUSIVE_OFFERS(
        titleResId = R.string.exclusive_offer,
        emptyStateResId = R.string.no_exclusive_offers
    ),
    BEST_SELLING(
        titleResId = R.string.best_selling,
        emptyStateResId = R.string.no_best_selling
    ),
    GROCERIES(
        titleResId = R.string.groceries,
        emptyStateResId = R.string.no_grocery_products
    )
}