package com.mashaal.ecommerce_app.ui.SeeAllScreen

import androidx.annotation.StringRes
import com.mashaal.ecommerce_app.R

enum class SeeAllSectionType(
    @StringRes val titleResId: Int
) {
    EXCLUSIVE_OFFERS(R.string.exclusive_offers),
    BEST_SELLING(R.string.best_selling_products),
    GROCERIES(R.string.groceries_products)
}