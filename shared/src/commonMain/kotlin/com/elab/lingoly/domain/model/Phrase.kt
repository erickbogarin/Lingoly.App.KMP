package com.elab.lingoly.domain.model

data class Phrase(
    val id: String,
    val dialogId: String,
    val translations: Translation,
    val tips: Translation
) {
    fun getText(language: Language): String {
        return translations.getOrDefault(language)
    }

    fun getTip(language: Language): String {
        return tips.getOrDefault(language)
    }

    fun hasTranslation(language: Language): Boolean {
        return translations.get(language) != null
    }

    fun hasTips(language: Language): Boolean {
        return tips.get(language) != null
    }

    fun getAvailableLanguages(): Set<Language> {
        return translations.translations.keys;
    }
}
