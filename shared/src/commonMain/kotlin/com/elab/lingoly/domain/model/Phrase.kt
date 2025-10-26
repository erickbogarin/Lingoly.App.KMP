package com.elab.lingoly.domain.model

data class Phrase(
    val id: String,
    val dialogId: String,
    val role: String = CommonRoles.GENERIC,
    val translations: Translation,
    val tips: Translation
) {
    fun getText(language: Language): String = translations.getOrDefault(language)

    fun getTip(language: Language): String = tips.getOrDefault(language)

    fun hasTranslation(language: Language): Boolean = translations.get(language) != null

    fun hasTip(language: Language): Boolean = tips.get(language) != null

    fun getAvailableLanguages(): Set<Language> = translations.translations.keys
}
