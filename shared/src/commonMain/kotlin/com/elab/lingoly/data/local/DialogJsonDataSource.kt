package com.elab.lingoly.data.local

import com.elab.lingoly.data.model.dialog.DialogDataDto
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class DialogJsonDataSource(private val jsonString: String) {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = false
        coerceInputValues = true // Handle null/missing values gracefully
    }

    /**
     * Lazily parsed dialog data.
     * Uses PUBLICATION mode for KMP compatibility (works on both JVM and Native).
     *
     * PUBLICATION mode:
     * - Android: Multiple threads may initialize, but first result wins
     * - iOS: Single-threaded access, no overhead
     * - Safe because initialization is deterministic (same JSON → same result)
     */
    private val lazyDialogData = lazy(LazyThreadSafetyMode.PUBLICATION) {
        try {
            json.decodeFromString<DialogDataDto>(jsonString)
        } catch (e: SerializationException) {
            throw JsonParsingException("Failed to parse dialog data JSON", e)
        } catch (e: IllegalArgumentException) {
            throw JsonParsingException("Invalid JSON structure", e)
        }
    }

    /**
     * Gets the parsed dialog data.
     * First access triggers parsing; subsequent calls return cached value.
     *
     * @throws JsonParsingException if JSON is malformed
     */
    val dialogData: DialogDataDto
        get() = lazyDialogData.value

    /**
     * Checks if the data has been parsed and cached.
     *
     * @return true if data is loaded, false otherwise
     */
    fun isCached(): Boolean = lazyDialogData.isInitialized()

    /**
     * Creates a new instance with cleared cache.
     * Original instance remains unchanged (immutability).
     *
     * Useful for:
     * - Testing different JSON payloads
     * - Memory pressure scenarios
     *
     * @return New DialogJsonDataSource instance with same JSON
     */
    fun clearCache(): DialogJsonDataSource =
        DialogJsonDataSource(jsonString)
}

/**
 * Exception thrown when JSON parsing fails.
 * Provides context about the parsing error.
 */
class JsonParsingException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)