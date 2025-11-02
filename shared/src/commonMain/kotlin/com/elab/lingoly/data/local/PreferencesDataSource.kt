package com.elab.lingoly.data.local

import com.elab.lingoly.data.model.user.UserDataDto
import com.elab.lingoly.utils.Constants
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PreferencesDataSource(private val settings: Settings) {
    private val json = Json { ignoreUnknownKeys = true }

    private val _userDataFlow = MutableStateFlow(loadUserData())
    val userDataFlow: StateFlow<UserDataDto> = _userDataFlow.asStateFlow()

    fun loadUserData(): UserDataDto {
        val name = settings.getString(Constants.KEY_USER_NAME, "")
        val savedPhrasesJson = settings.getString(Constants.KEY_SAVED_PHRASES, "[]")
        val completedDialogsJson = settings.getString(Constants.KEY_COMPLETED_DIALOGS, "[]")
        val xp = settings.getInt(Constants.KEY_XP, 0)
        val streak = settings.getInt(Constants.KEY_STREAK, 0)
        val achievementsJson = settings.getString(Constants.KEY_ACHIEVEMENTS, "[]")

        val nativeLang = settings.getString(Constants.KEY_NATIVE_LANGUAGE, Constants.DEFAULT_NATIVE_LANGUAGE)
        val targetLang = settings.getString(Constants.KEY_TARGET_LANGUAGE, Constants.DEFAULT_TARGET_LANGUAGE)
        val hasCompleted = settings.getBoolean(Constants.KEY_ONBOARDING_COMPLETED, false)

        return UserDataDto(
            name = name,
            completedDialogs = json.decodeFromString(completedDialogsJson),
            savedPhrases = json.decodeFromString(savedPhrasesJson),
            xp = xp,
            streak = streak,
            achievements = json.decodeFromString(achievementsJson),
            nativeLanguage = nativeLang,
            targetLanguage = targetLang,
            hasCompletedOnboarding = hasCompleted
        )
    }

    fun saveUserData(userData: UserDataDto) {
        settings.putString(Constants.KEY_USER_NAME, userData.name)
        settings.putString(Constants.KEY_SAVED_PHRASES, json.encodeToString(userData.savedPhrases))
        settings.putString(Constants.KEY_COMPLETED_DIALOGS, json.encodeToString(userData.completedDialogs))
        settings.putInt(Constants.KEY_XP, userData.xp)
        settings.putInt(Constants.KEY_STREAK, userData.streak)
        settings.putString(Constants.KEY_ACHIEVEMENTS, json.encodeToString(userData.achievements))

        settings.putString(Constants.KEY_NATIVE_LANGUAGE, userData.nativeLanguage)
        settings.putString(Constants.KEY_TARGET_LANGUAGE, userData.targetLanguage)
        settings.putBoolean(Constants.KEY_ONBOARDING_COMPLETED, userData.hasCompletedOnboarding)

        _userDataFlow.value = userData
    }
}
