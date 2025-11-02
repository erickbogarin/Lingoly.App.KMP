package com.elab.lingoly.testutils.data

import com.elab.lingoly.data.model.user.UserDataDto

class UserDataDtoBuilder {
    var name: String = "Default User"
    var completedDialogs: List<String> = emptyList()
    var savedPhrases: List<String> = emptyList()
    var xp: Int = 0
    var streak: Int = 0
    var achievements: List<String> = emptyList()
    var nativeLanguage: String = "pt"
    var targetLanguage: String = "en"
    var hasCompletedOnboarding: Boolean = false

    fun name(value: String) = apply { name = value }
    fun completed(vararg dialogs: String) = apply { completedDialogs = dialogs.toList() }
    fun saved(vararg phrases: String) = apply { savedPhrases = phrases.toList() }
    fun xp(value: Int) = apply { xp = value }
    fun streak(value: Int) = apply { streak = value }
    fun achievements(vararg values: String) = apply { achievements = values.toList() }
    fun native(langCode: String) = apply { nativeLanguage = langCode }
    fun target(langCode: String) = apply { targetLanguage = langCode }
    fun completedOnboarding(value: Boolean = true) = apply { hasCompletedOnboarding = value }

    fun emptyDefaults() = apply {
        name = ""
        completedDialogs = emptyList()
        savedPhrases = emptyList()
        xp = 0
        streak = 0
        achievements = emptyList()
        nativeLanguage = "pt"
        targetLanguage = "en"
        hasCompletedOnboarding = false
    }

    fun build() = UserDataDto(
        name = name,
        completedDialogs = completedDialogs,
        savedPhrases = savedPhrases,
        xp = xp,
        streak = streak,
        achievements = achievements,
        nativeLanguage = nativeLanguage,
        targetLanguage = targetLanguage,
        hasCompletedOnboarding = hasCompletedOnboarding
    )
}
