package com.mashaal.ecommerce_app.di

import com.mashaal.ecommerce_app.data.repository.ProductRepositoryImpl
import com.mashaal.ecommerce_app.data.repository.CartRepositoryImpl
import com.mashaal.ecommerce_app.domain.repository.ProductRepository
import com.mashaal.ecommerce_app.domain.repository.CartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
    
    @Binds
    @Singleton
    abstract fun bindCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository
}