package com.mashaal.ecommerce_app.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.mashaal.ecommerce_app.data.Dao.ProductDao
import com.mashaal.ecommerce_app.data.Database.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideProductDatabase(@ApplicationContext context: Context): ProductDatabase {
        return databaseBuilder(
            context,
            ProductDatabase::class.java,
            "product_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: ProductDatabase): ProductDao {
        return database.productDao()
    }
}