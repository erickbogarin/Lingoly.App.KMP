package com.elab.lingoly.domain.validators

import com.elab.lingoly.domain.model.Language
import kotlin.test.*

class LanguageValidatorTest {
    @Test
    fun `validateLanguagePair should return Valid for different languages`() {
        val result = LanguageValidator.validateLanguagePair(
            Language.PORTUGUESE,
            Language.ENGLISH
        )

        assertTrue(result is LanguageValidator.ValidationResult.Valid)
    }

    @Test
    fun `validateLanguagePair should return Invalid when native is null`() {
        val result = LanguageValidator.validateLanguagePair(
            null,
            Language.ENGLISH
        )

        assertTrue(result is LanguageValidator.ValidationResult.Invalid)
        assertEquals(
            "Please select your native language",
            (result as LanguageValidator.ValidationResult.Invalid).message
        )
    }

    @Test
    fun `validateLanguagePair should return Invalid when target is null`() {
        val result = LanguageValidator.validateLanguagePair(
            Language.PORTUGUESE,
            null
        )

        assertTrue(result is LanguageValidator.ValidationResult.Invalid)
        assertEquals(
            "Please select the language you want to learn",
            (result as LanguageValidator.ValidationResult.Invalid).message
        )
    }

    @Test
    fun `validateLanguagePair should return Invalid when languages are same`() {
        val result = LanguageValidator.validateLanguagePair(
            Language.ENGLISH,
            Language.ENGLISH
        )

        assertTrue(result is LanguageValidator.ValidationResult.Invalid)
        assertEquals(
            "Native and target languages must be different",
            (result as LanguageValidator.ValidationResult.Invalid).message
        )
    }

    @Test
    fun `isValidPair should return true for valid pair`() {
        assertTrue(
            LanguageValidator.isValidPair(
                Language.PORTUGUESE,
                Language.SPANISH
            )
        )
    }

    @Test
    fun `isValidPair should return false when native is null`() {
        assertFalse(
            LanguageValidator.isValidPair(
                null,
                Language.ENGLISH
            )
        )
    }

    @Test
    fun `isValidPair should return false when target is null`() {
        assertFalse(
            LanguageValidator.isValidPair(
                Language.PORTUGUESE,
                null
            )
        )
    }

    @Test
    fun `isValidPair should return false when languages are same`() {
        assertFalse(
            LanguageValidator.isValidPair(
                Language.SPANISH,
                Language.SPANISH
            )
        )
    }
}