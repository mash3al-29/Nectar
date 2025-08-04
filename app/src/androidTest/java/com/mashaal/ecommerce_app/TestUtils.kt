package com.mashaal.ecommerce_app

import com.mashaal.ecommerce_app.data.Entity.ProductEntity
import com.mashaal.ecommerce_app.domain.model.CartItem
import com.mashaal.ecommerce_app.domain.model.Product

class TestUtils {
    companion object {

        fun testProduct(
            id: Int = 1,
            name: String = "Test Product",
            detail: String = "Details",
            imageUrl: String = "http://example.com/image.png",
            price: Double = 10.0,
            description: String = "Description",
            category: String = "Category",
            nutrition: Map<String, String> = mapOf("Calories" to "200"),
            review: Int = 4
        ): ProductEntity {
            return ProductEntity(
                id = id,
                name = name,
                detail = detail,
                imageUrl = imageUrl,
                price = price,
                description = description,
                category = category,
                nutrition = nutrition,
                review = review
            )
        }

        fun testDomainProduct(
            id: Int = 1,
            name: String = "Test Product",
            detail: String = "Details",
            imageUrl: String = "http://example.com/image.png",
            price: Double = 10.0,
            description: String = "Description",
            category: String = "Category",
            nutrition: Map<String, String> = mapOf("Calories" to "200"),
            review: Int = 4
        ): Product {
            return testProduct(
                id, name, detail, imageUrl, price,
                description, category, nutrition, review
            ).toDomainModel()
        }

        fun testCartItem(
            productId: Int = 1,
            quantity: Int = 2,
            portion: String = "Medium",
            price: Double = 10.0,
            addedAt: Long = System.currentTimeMillis()
        ): CartItem {
            val product = testDomainProduct(id = productId, price = price)
            return CartItem(
                product = product,
                quantity = quantity,
                portion = portion,
                addedAt = addedAt
            )
        }
    }
} 