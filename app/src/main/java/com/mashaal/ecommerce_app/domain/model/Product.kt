package com.mashaal.ecommerce_app.domain.model

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