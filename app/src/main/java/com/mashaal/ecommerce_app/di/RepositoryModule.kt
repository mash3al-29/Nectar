package com.mashaal.ecommerce_app.di

<<<<<<< HEAD
=======
import com.mashaal.ecommerce_app.data.Dao.ProductDao
>>>>>>> a5a627e (feature: added categories screen, category details screen and filters bottom sheet (will update since it has some issues), also removed the old mock system and replaced it with the real database)
import com.mashaal.ecommerce_app.data.repository.ProductRepositoryImpl
import com.mashaal.ecommerce_app.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
<<<<<<< HEAD
    fun provideProductRepositoryImpl(): ProductRepository {
        return ProductRepositoryImpl()
=======
    fun provideProductRepositoryImpl(productDao: ProductDao): ProductRepository {
        return ProductRepositoryImpl(productDao)
>>>>>>> a5a627e (feature: added categories screen, category details screen and filters bottom sheet (will update since it has some issues), also removed the old mock system and replaced it with the real database)
    }
}