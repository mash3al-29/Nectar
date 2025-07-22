package com.mashaal.ecommerce_app.data.repository

import com.mashaal.ecommerce_app.domain.model.Product
import com.mashaal.ecommerce_app.domain.repository.ProductRepository
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl() : ProductRepository {
    private val products = listOf(
        Product(
            id = 1,
            name = "Organic Bananas",
            imageUrl = "https://t3.ftcdn.net/jpg/02/99/43/48/360_F_299434842_UF1e0k44KUpkdtAEu0XbbPVnTHFuRwAm.jpg",
            price = 4.99,
            review = 2,
            detail = "Organic",
            description = "Organic bananas are sweeter than conventional bananas because they're allowed to ripen naturally on the tree. They're grown without synthetic pesticides or fertilizers.",
            nutrition = mapOf(
                "Energy" to "89 kcal",
                "Protein" to "1.1 g",
                "Carbohydrates" to "22.8 g",
                "Fat" to "0.3 g",
                "Fiber" to "2.6 g"
            ),
            category = "Fresh Fruits and Vegetables"
        ),
        Product(
            id = 2,
            name = "Red Apple",
            imageUrl = "https://t3.ftcdn.net/jpg/02/99/43/48/360_F_299434842_UF1e0k44KUpkdtAEu0XbbPVnTHFuRwAm.jpg",
            price = 1.99,
            category = "Fresh Fruits and Vegetables",
            detail = "Fresh",
            description = "Red apples are known for their sweet flavor and crisp texture. They're a good source of fiber and vitamin C.",
            nutrition = mapOf(
                "Energy" to "52 kcal",
                "Protein" to "0.3 g",
                "Carbohydrates" to "14 g",
                "Fat" to "0.2 g",
                "Fiber" to "2.4 g"
            ),
            review = 4
        ),
        Product(
            id = 3,
            name = "Bell Pepper",
            imageUrl = "https://t3.ftcdn.net/jpg/02/99/43/48/360_F_299434842_UF1e0k44KUpkdtAEu0XbbPVnTHFuRwAm.jpg",
            price = 2.99,
            category = "Fresh Fruits and Vegetables",
            detail = "Fresh",
            description = "Bell peppers are rich in vitamins and antioxidants, particularly vitamin C and various carotenoids.",
            nutrition = mapOf(
                "Energy" to "31 kcal",
                "Protein" to "1 g",
                "Carbohydrates" to "6 g",
                "Fat" to "0.3 g",
                "Fiber" to "2.1 g"
            ),
            review = 3
        )
    )
    
    override suspend fun getProductById(id: Int): Product? {
        return products.find { it.id == id }
    }
    
    override suspend fun getAllProducts(): List<Product> {
        return products
    }
}