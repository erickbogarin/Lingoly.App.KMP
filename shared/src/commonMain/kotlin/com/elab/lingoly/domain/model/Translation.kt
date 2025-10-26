package com.elab.lingoly.domain.model

data class Translation(
    val translations: Map<Language, String>
) {
    fun get(language: Language): String? = translations[language]

    fun getOrDefault(language: Language, fallback: Language = Language.ENGLISH): String {
        return translations[language] ?: translations[fallback] ?: translations.values.first()
    }
}
