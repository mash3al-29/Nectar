package com.mashaal.ecommerce_app.di

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
    fun provideProductRepositoryImpl(): ProductRepository {
        return ProductRepositoryImpl()
    }
}