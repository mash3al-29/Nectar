package com.mashaal.ecommerce_app.di

import com.mashaal.ecommerce_app.domain.repository.ProductRepository
import com.mashaal.ecommerce_app.domain.repository.CartRepository
import com.mashaal.ecommerce_app.domain.usecase.*
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

    @Provides
    @Singleton
    fun provideGetAllCategories(productRepository: ProductRepository): GetAllCategoriesUseCase {
        return GetAllCategoriesUseCase(productRepository)
    }
    @Provides
    @Singleton
    fun provideGetProductsByCategory(productRepository: ProductRepository): GetProductsByCategoryUseCase {
        return GetProductsByCategoryUseCase(productRepository)
    }
    
    @Provides
    @Singleton
    fun provideSearchProductsUseCase(productRepository: ProductRepository): SearchProductsUseCase {
        return SearchProductsUseCase(productRepository)
    }
    
    @Provides
    @Singleton
    fun provideGetCartUseCase(cartRepository: CartRepository): GetCartUseCase {
        return GetCartUseCase(cartRepository)
    }
    
    @Provides
    @Singleton
    fun provideAddToCartUseCase(cartRepository: CartRepository): AddToCartUseCase {
        return AddToCartUseCase(cartRepository)
    }
    
    @Provides
    @Singleton
    fun provideRemoveFromCartUseCase(cartRepository: CartRepository): RemoveFromCartUseCase {
        return RemoveFromCartUseCase(cartRepository)
    }
    
    @Provides
    @Singleton
    fun provideUpdateCartQuantityUseCase(cartRepository: CartRepository): UpdateCartQuantityUseCase {
        return UpdateCartQuantityUseCase(cartRepository)
    }
    
    @Provides
    @Singleton
    fun provideGetCartItemCountUseCase(cartRepository: CartRepository): GetCartItemCountUseCase {
        return GetCartItemCountUseCase(cartRepository)
    }
    
    @Provides
    @Singleton
    fun provideGetCartTotalQuantityUseCase(cartRepository: CartRepository): GetCartTotalQuantityUseCase {
        return GetCartTotalQuantityUseCase(cartRepository)
    }
}