package com.elab.lingoly.domain.model

import kotlin.test.*

class TranslationTest {

    private val translation = Translation(
        translations = mapOf(
            Language.ENGLISH to "Hello",
            Language.PORTUGUESE to "Olá"
        )
    )

    @Test
    fun `should return translation when language exists`() {
        assertEquals("Olá", translation.getOrDefault(Language.PORTUGUESE))
    }

    @Test
    fun `should fallback to English when language missing`() {
        assertEquals("Hello", translation.getOrDefault(Language.SPANISH))
    }
}