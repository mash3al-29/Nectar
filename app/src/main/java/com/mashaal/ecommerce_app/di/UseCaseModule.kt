package com.mashaal.ecommerce_app.di

import com.mashaal.ecommerce_app.domain.repository.ProductRepository
import com.mashaal.ecommerce_app.domain.usecase.GetAllProductsUseCase
import com.mashaal.ecommerce_app.domain.usecase.GetProductByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun getProductByIdUseCase(productRepository: ProductRepository): GetProductByIdUseCase {
        return GetProductByIdUseCase(productRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllProducts(productRepository: ProductRepository): GetAllProductsUseCase {
        return GetAllProductsUseCase(productRepository)
    }

}