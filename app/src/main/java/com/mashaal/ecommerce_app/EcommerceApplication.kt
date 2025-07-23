package com.mashaal.ecommerce_app

import android.app.Application

import com.mashaal.ecommerce_app.domain.usecase.GetAllProductsUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class EcommerceApplication : Application() {
     @Inject
     lateinit var getAllProductsUseCase: GetAllProductsUseCase

     private val applicationScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        initializeDatabase()
    }

    private fun initializeDatabase() {
        applicationScope.launch {
            getAllProductsUseCase.execute()
        }
    }
}