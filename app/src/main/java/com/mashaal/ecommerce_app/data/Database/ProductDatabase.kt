package com.mashaal.ecommerce_app.data.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mashaal.ecommerce_app.data.Dao.ProductDao
import com.mashaal.ecommerce_app.data.Entity.ProductEntity
import com.mashaal.ecommerce_app.data.util.MapConverter

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
@TypeConverters(MapConverter::class)
abstract class ProductDatabase : RoomDatabase(){
    abstract fun productDao(): ProductDao
}