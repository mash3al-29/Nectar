package com.mashaal.ecommerce_app.data.util

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class FirstTimeManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val PREFS_NAME = "app_preferences"
        private const val KEY_FIRST_TIME = "is_first_time"
    }
    
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    suspend fun isFirstTime(): Boolean = withContext(Dispatchers.IO) {
        sharedPreferences.getBoolean(KEY_FIRST_TIME, true)
    }

    suspend fun setFirstTimeCompleted() = withContext(Dispatchers.IO) {
        sharedPreferences.edit { putBoolean(KEY_FIRST_TIME, false) }
    }

} 