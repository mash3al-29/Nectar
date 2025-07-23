package com.mashaal.ecommerce_app.data.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mashaal.ecommerce_app.domain.model.Product

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val detail: String,
    val imageUrl: String,
    val price: Double,
    val description: String,
    val category: String,
    val nutrition: Map<String, String>,
    val review: Int
) {
    fun toDomainModel(): Product {
        return Product(
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

    companion object{
        fun fromDomainModel(product: Product): ProductEntity{
            return ProductEntity(
                id = product.id,
                name = product.name,
                detail = product.detail,
                imageUrl = product.imageUrl,
                price = product.price,
                description = product.description,
                category = product.category,
                nutrition = product.nutrition,
                review = product.review
            )
        }
    }
}