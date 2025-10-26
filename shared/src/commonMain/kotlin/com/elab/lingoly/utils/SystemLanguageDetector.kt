package com.elab.lingoly.utils

import com.elab.lingoly.domain.model.Language

/**
 * Platform-specific system language detection.
 * Sprint 2 will implement actual objects for Android and iOS.
 *
 * For now, this is just the interface definition.
 */
//expect object SystemLanguageDetector {
//    /**
//     * Detects the system language and returns the closest supported Language.
//     * Falls back to English if system language is not supported.
//     */
//    fun getSystemLanguage(): Language
//
//    /**
//     * Gets suggested target language based on native language.
//     * Used for smart suggestions in onboarding.
//     */
//    fun getSuggestedTargetLanguage(nativeLanguage: Language): Language
//}