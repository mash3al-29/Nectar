package com.mashaal.ecommerce_app.data.util

import androidx.room.TypeConverter

class MapConverter {
    @TypeConverter
    fun fromMap(map: Map<String, String>?): String? {
        return map?.entries?.joinToString(";") { "${it.key}=${it.value}" }
    }
    
    @TypeConverter
    fun toMap(data: String?): Map<String, String>? {
        return data?.takeIf { it.isNotBlank() }?.split(";")?.associate {
            val parts = it.split("=", limit = 2)
            if (parts.size == 2) {
                parts[0] to parts[1]
            } else {
                parts[0] to ""
            }
        }
    }
}
