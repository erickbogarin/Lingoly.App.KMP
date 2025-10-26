package com.elab.lingoly.domain.validators

import com.elab.lingoly.domain.model.Language

object LanguageValidator {

    sealed class ValidationResult {
        object Valid : ValidationResult()
        data class Invalid(val message: String) : ValidationResult()
    }

    /**
     * Validates a pair of languages for learning setup.
     *
     * Rules:
     * - Both languages must be selected
     * - Languages must be different
     */
    fun validateLanguagePair(
        native: Language?,
        target: Language?
    ): ValidationResult {
        return when {
            native == null -> ValidationResult.Invalid("Please select your native language")
            target == null -> ValidationResult.Invalid("Please select the language you want to learn")
            native == target -> ValidationResult.Invalid("Native and target languages must be different")
            else -> ValidationResult.Valid
        }
    }

    /**
     * Checks if a language pair is valid without returning error message.
     * Useful for enabling/disabling UI buttons.
     */
    fun isValidPair(native: Language?, target: Language?): Boolean {
        return native != null && target != null && native != target
    }
}