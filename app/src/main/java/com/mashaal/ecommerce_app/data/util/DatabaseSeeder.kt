package com.mashaal.ecommerce_app.data.util

import com.mashaal.ecommerce_app.data.Dao.ProductDao
import com.mashaal.ecommerce_app.data.Entity.ProductEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseSeeder @Inject constructor(
    private val productDao: ProductDao
) {
    suspend fun seedDatabaseIfEmpty() {
        val sampleProducts = listOf(
            // Fresh Fruits & Vegetable
            ProductEntity(
                id = 0,
                name = "Natural Red Apple",
                detail = "1kg",
                imageUrl = "https://img.freepik.com/premium-photo/red-apple-with-white-background-shadow-it_14117-4740.jpg",
                price = 4.99,
                description = "Apples are nutritious. Apples may be good for weight loss. Apples may be good for your heart. As part of a healthful and varied diet.",
                category = "Fresh Fruits & Vegetable",
                nutrition = mapOf(
                    "Energy" to "52 kcal",
                    "Protein" to "0.3 g",
                    "Carbohydrates" to "14 g",
                    "Fat" to "0.2 g",
                    "Fiber" to "2.4 g"
                ),
                review = 5
            ),
            ProductEntity(
                id = 0,
                name = "Carrot",
                detail = "1kg",
                imageUrl = "https://t3.ftcdn.net/jpg/02/99/43/48/360_F_299434842_UF1e0k44KUpkdtAEu0XbbPVnTHFuRwAm.jpg",
                price = 1.99,
                description = "Carrots are a great source of beta carotene, fiber, vitamin K1, and antioxidants.",
                category = "Fresh Fruits & Vegetable",
                nutrition = mapOf(
                    "Energy" to "41 kcal",
                    "Protein" to "0.9 g",
                    "Carbohydrates" to "10 g",
                    "Fat" to "0.2 g",
                    "Fiber" to "2.8 g"
                ),
                review = 4
            ),
            ProductEntity(
                id = 0,
                name = "Cucumber",
                detail = "1kg",
                imageUrl = "https://img.freepik.com/premium-photo/cucumber-isolated-white-background_319514-5406.jpg",
                price = 1.49,
                description = "Cucumbers are low in calories and high in water and important vitamins and minerals.",
                category = "Fresh Fruits & Vegetable",
                nutrition = mapOf(
                    "Energy" to "16 kcal",
                    "Protein" to "0.7 g",
                    "Carbohydrates" to "3.6 g",
                    "Fat" to "0.1 g",
                    "Fiber" to "0.5 g"
                ),
                review = 3
            ),
            
            // Cooking Oil & Ghee
            ProductEntity(
                id = 0,
                name = "Sunflower Oil",
                detail = "1L",
                imageUrl = "https://purepng.com/public/uploads/large/purepng.com-sunflower-oilsunflower-oilcooking-oilfrying-oilnon-volatile-oil-1411529833165ctzjx.png",
                price = 5.49,
                description = "Sunflower oil is light, healthy, and ideal for cooking and frying.",
                category = "Cooking Oil & Ghee",
                nutrition = mapOf(
                    "Energy" to "884 kcal",
                    "Protein" to "0 g",
                    "Carbohydrates" to "0 g",
                    "Fat" to "100 g",
                    "Fiber" to "0 g"
                ),
                review = 4
            ),
            ProductEntity(
                id = 0,
                name = "Pure Ghee",
                detail = "500g",
                imageUrl = "https://rosepng.com/wp-content/uploads/2025/01/desi-ghee-1.png",
                price = 6.99,
                description = "Ghee is rich in fat-soluble vitamins and is used widely in traditional recipes.",
                category = "Cooking Oil & Ghee",
                nutrition = mapOf(
                    "Energy" to "900 kcal",
                    "Protein" to "0 g",
                    "Carbohydrates" to "0 g",
                    "Fat" to "100 g",
                    "Fiber" to "0 g"
                ),
                review = 5
            ),
            ProductEntity(
                id = 0,
                name = "Canola Oil",
                detail = "1L",
                imageUrl = "https://www.pikpng.com/pngl/b/197-1975663_oil-sunflower-canola-cooking-seed-rapeseed-oils-clipart.png",
                price = 4.79,
                description = "Canola oil is low in saturated fat and good for heart health.",
                category = "Cooking Oil & Ghee",
                nutrition = mapOf(
                    "Energy" to "884 kcal",
                    "Protein" to "0 g",
                    "Carbohydrates" to "0 g",
                    "Fat" to "100 g",
                    "Fiber" to "0 g"
                ),
                review = 4
            ),
            
            // Meat & Fish
            ProductEntity(
                id = 0,
                name = "Fresh Chicken Breast",
                detail = "500g",
                imageUrl = "https://cdn.pixabay.com/photo/2014/03/05/01/20/chicken-breast-279849_1280.jpg",
                price = 7.99,
                description = "Lean and protein-rich, ideal for healthy cooking.",
                category = "Meat & Fish",
                nutrition = mapOf(
                    "Energy" to "165 kcal",
                    "Protein" to "31 g",
                    "Carbohydrates" to "0 g",
                    "Fat" to "3.6 g",
                    "Fiber" to "0 g"
                ),
                review = 5
            ),
            ProductEntity(
                id = 0,
                name = "Salmon Fillet",
                detail = "300g",
                imageUrl = "https://cdn.pixabay.com/photo/2021/05/25/11/43/fish-6282216_1280.jpg",
                price = 9.99,
                description = "Salmon is rich in omega-3 fatty acids and excellent for heart and brain health.",
                category = "Meat & Fish",
                nutrition = mapOf(
                    "Energy" to "208 kcal",
                    "Protein" to "20 g",
                    "Carbohydrates" to "0 g",
                    "Fat" to "13 g",
                    "Fiber" to "0 g"
                ),
                review = 5
            ),
            ProductEntity(
                id = 0,
                name = "Beef Steak",
                detail = "1kg",
                imageUrl = "https://cdn.pixabay.com/photo/2018/02/08/15/02/meat-3139641_1280.jpg",
                price = 12.49,
                description = "High-protein and iron-rich red meat.",
                category = "Meat & Fish",
                nutrition = mapOf(
                    "Energy" to "250 kcal",
                    "Protein" to "26 g",
                    "Carbohydrates" to "0 g",
                    "Fat" to "17 g",
                    "Fiber" to "0 g"
                ),
                review = 4
            ),
            
            // Bakery & Snacks
            ProductEntity(
                id = 0,
                name = "Whole Wheat Bread",
                detail = "400g",
                imageUrl = "https://static.vecteezy.com/system/resources/previews/002/463/399/large_2x/slice-whole-wheat-bread-isolated-on-white-background-free-photo.jpg",
                price = 2.19,
                description = "Made with whole grains, good source of fiber.",
                category = "Bakery & Snacks",
                nutrition = mapOf(
                    "Energy" to "247 kcal",
                    "Protein" to "13 g",
                    "Carbohydrates" to "41 g",
                    "Fat" to "4.2 g",
                    "Fiber" to "7 g"
                ),
                review = 4
            ),
            ProductEntity(
                id = 0,
                name = "Butter Croissant",
                detail = "1pc",
                imageUrl = "https://cdn.pixabay.com/photo/2012/02/29/12/17/bread-18987_1280.jpg",
                price = 1.49,
                description = "Flaky and buttery pastry, perfect for breakfast.",
                category = "Bakery & Snacks",
                nutrition = mapOf(
                    "Energy" to "406 kcal",
                    "Protein" to "8 g",
                    "Carbohydrates" to "45 g",
                    "Fat" to "21 g",
                    "Fiber" to "2.6 g"
                ),
                review = 5
            ),
            ProductEntity(
                id = 0,
                name = "Potato Chips",
                detail = "150g",
                imageUrl = "https://cdn.pixabay.com/photo/2022/12/05/17/51/potato-chips-7637285_1280.jpg",
                price = 1.99,
                description = "Crispy, salted, and addictive snack.",
                category = "Bakery & Snacks",
                nutrition = mapOf(
                    "Energy" to "536 kcal",
                    "Protein" to "7 g",
                    "Carbohydrates" to "53 g",
                    "Fat" to "34 g",
                    "Fiber" to "4.8 g"
                ),
                review = 4
            ),
            
            // Dairy & Eggs
            ProductEntity(
                id = 0,
                name = "Fresh Milk",
                detail = "1L",
                imageUrl = "https://img.freepik.com/premium-photo/glass-bottle-fresh-milk-isolated-white-background_252965-47.jpg",
                price = 2.29,
                description = "Rich in calcium and vitamin D for bone health.",
                category = "Dairy & Eggs",
                nutrition = mapOf(
                    "Energy" to "42 kcal",
                    "Protein" to "3.4 g",
                    "Carbohydrates" to "5 g",
                    "Fat" to "1 g",
                    "Fiber" to "0 g"
                ),
                review = 5
            ),
            ProductEntity(
                id = 0,
                name = "Organic Eggs",
                detail = "12pcs",
                imageUrl = "https://img.freepik.com/free-photo/three-fresh-organic-raw-eggs-isolated-white-surface_114579-43677.jpg",
                price = 3.49,
                description = "High in protein and healthy fats.",
                category = "Dairy & Eggs",
                nutrition = mapOf(
                    "Energy" to "155 kcal",
                    "Protein" to "13 g",
                    "Carbohydrates" to "1.1 g",
                    "Fat" to "11 g",
                    "Fiber" to "0 g"
                ),
                review = 5
            ),
            ProductEntity(
                id = 0,
                name = "Cheddar Cheese",
                detail = "250g",
                imageUrl = "https://img.freepik.com/premium-photo/cheddar-cheese-isolated-white-background_407474-20664.jpg",
                price = 4.59,
                description = "Bold, tangy flavor great for sandwiches and cooking.",
                category = "Dairy & Eggs",
                nutrition = mapOf(
                    "Energy" to "403 kcal",
                    "Protein" to "25 g",
                    "Carbohydrates" to "1.3 g",
                    "Fat" to "33 g",
                    "Fiber" to "0 g"
                ),
                review = 4
            ),
            
            // Beverages
            ProductEntity(
                id = 0,
                name = "Orange Juice",
                detail = "1L",
                imageUrl = "https://img.freepik.com/premium-photo/orange-juice-white-background_269353-1137.jpg",
                price = 3.79,
                description = "Freshly squeezed and full of vitamin C.",
                category = "Beverages",
                nutrition = mapOf(
                    "Energy" to "45 kcal",
                    "Protein" to "0.7 g",
                    "Carbohydrates" to "10.4 g",
                    "Fat" to "0.2 g",
                    "Fiber" to "0.2 g"
                ),
                review = 5
            ),
            ProductEntity(
                id = 0,
                name = "Green Tea",
                detail = "25 bags",
                imageUrl = "https://img.freepik.com/premium-photo/cup-green-tea-with-leaves-white-background_787273-2374.jpg",
                price = 2.49,
                description = "Boosts metabolism and rich in antioxidants.",
                category = "Beverages",
                nutrition = mapOf(
                    "Energy" to "1 kcal",
                    "Protein" to "0 g",
                    "Carbohydrates" to "0 g",
                    "Fat" to "0 g",
                    "Fiber" to "0 g"
                ),
                review = 4
            ),
            ProductEntity(
                id = 0,
                name = "Cola Drink",
                detail = "500ml",
                imageUrl = "https://img.freepik.com/premium-photo/drink-cola-with-ice-glass-white-background_55716-123.jpg",
                price = 1.19,
                description = "Classic carbonated soft drink.",
                category = "Beverages",
                nutrition = mapOf(
                    "Energy" to "42 kcal",
                    "Protein" to "0 g",
                    "Carbohydrates" to "11 g",
                    "Fat" to "0 g",
                    "Fiber" to "0 g"
                ),
                review = 4
            )
        )
        productDao.insertProducts(sampleProducts)
    }
} 