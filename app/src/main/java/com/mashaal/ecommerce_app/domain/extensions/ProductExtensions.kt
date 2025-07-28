package com.mashaal.ecommerce_app.domain.extensions

import com.mashaal.ecommerce_app.domain.model.Product

// placeholders for actual fetching of isexclusive, isbestselling and groceries
val Product.isExclusiveOffer: Boolean
    get() = id % 3 == 0

val Product.isBestSelling: Boolean
    get() = id % 5 == 0

val Product.groceries: Boolean
    get() = id % 2 == 0