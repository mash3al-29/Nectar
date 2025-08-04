package com.mashaal.ecommerce_app.domain.model

import com.mashaal.ecommerce_app.data.Entity.ProductEntity

data class Product(
    val id: Int = 0,
    val name: String,
    val detail: String,
    val imageUrl: String,
    val price: Double,
    val description: String,
    val category: String,
    val nutrition: Map<String, String>,
    val review: Int
)

fun ProductEntity.toDomain(): Product = Product(
    id = id,
    name = name,
    description = description,
    price = price,
    imageUrl = imageUrl,
    category = category,
    detail = detail,
    nutrition = nutrition,
    review = review
)
