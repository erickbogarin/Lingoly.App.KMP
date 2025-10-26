package com.elab.lingoly.data.local

import com.elab.lingoly.data.model.LingolyDataDto
import kotlinx.serialization.json.Json

class JsonDataSource(private val jsonString: String) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = false
    }

    private var cachedData: LingolyDataDto? = null

    fun loadLingolyData(): LingolyDataDto {
        if (cachedData == null) {
            cachedData = json.decodeFromString<LingolyDataDto>(jsonString)
        }
        return cachedData!!
    }

    fun clearCache() {
        cachedData = null
    }
}