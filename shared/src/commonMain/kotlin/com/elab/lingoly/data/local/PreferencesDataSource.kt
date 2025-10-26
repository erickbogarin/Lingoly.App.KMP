package com.elab.lingoly.data.local

import com.elab.lingoly.data.model.user.UserDataDto
import com.elab.lingoly.utils.Constants
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

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

        return UserDataDto(
            name = name,
            completedDialogs = json.decodeFromString(completedDialogsJson),
            savedPhrases = json.decodeFromString(savedPhrasesJson),
            xp = xp,
            streak = streak,
            achievements = json.decodeFromString(achievementsJson)
        )
    }

    fun saveUserData(userData: UserDataDto) {
        settings.putString(Constants.KEY_USER_NAME, userData.name)
        settings.putString(Constants.KEY_SAVED_PHRASES, json.encodeToString(userData.savedPhrases))
        settings.putString(Constants.KEY_COMPLETED_DIALOGS, json.encodeToString(userData.completedDialogs))
        settings.putInt(Constants.KEY_XP, userData.xp)
        settings.putInt(Constants.KEY_STREAK, userData.streak)
        settings.putString(Constants.KEY_ACHIEVEMENTS, json.encodeToString(userData.achievements))

        _userDataFlow.value = userData
    }
}