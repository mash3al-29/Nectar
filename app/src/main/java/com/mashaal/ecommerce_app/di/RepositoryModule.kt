package com.mashaal.ecommerce_app.di


import com.mashaal.ecommerce_app.data.Dao.ProductDao
import com.mashaal.ecommerce_app.data.Dao.CartDao
import com.mashaal.ecommerce_app.data.repository.ProductRepositoryImpl
import com.mashaal.ecommerce_app.data.repository.CartRepositoryImpl
import com.mashaal.ecommerce_app.domain.repository.ProductRepository
import com.mashaal.ecommerce_app.domain.repository.CartRepository
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
    fun provideProductRepositoryImpl(productDao: ProductDao): ProductRepository {
        return ProductRepositoryImpl(productDao)
    }
    
    @Provides
    @Singleton
    fun provideCartRepositoryImpl(cartDao: CartDao, productDao: ProductDao): CartRepository {
        return CartRepositoryImpl(cartDao, productDao)
    }
}