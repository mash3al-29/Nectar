package com.mashaal.ecommerce_app.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mashaal.ecommerce_app.data.Dao.ProductDao
import com.mashaal.ecommerce_app.data.Dao.CartDao
import com.mashaal.ecommerce_app.data.Database.ProductDatabase
import com.mashaal.ecommerce_app.data.util.DatabaseSeeder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideProductDatabase(
        @ApplicationContext context: Context,
        seederProvider: Provider<DatabaseSeeder>
    ): ProductDatabase {
        return databaseBuilder(
            context,
            ProductDatabase::class.java,
            "product_database"
        )
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                CoroutineScope(Dispatchers.IO).launch {
                    seederProvider.get().seedDatabaseIfEmpty()
                }
            }
        })
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: ProductDatabase): ProductDao {
        return database.productDao()
    }
    
    @Provides
    @Singleton
    fun provideCartDao(database: ProductDatabase): CartDao {
        return database.cartDao()
    }
}