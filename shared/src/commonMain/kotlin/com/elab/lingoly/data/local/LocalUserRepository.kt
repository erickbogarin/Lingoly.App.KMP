package com.elab.lingoly.data.local

import com.elab.lingoly.data.mapper.UserDataMapper
import com.elab.lingoly.domain.model.Language
import com.elab.lingoly.domain.model.UserData
import com.elab.lingoly.domain.repository.UserRepository
import com.elab.lingoly.utils.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserRepository(
    private val preferencesDataSource: PreferencesDataSource
) : UserRepository {

    override suspend fun getUserData(): DataResult<UserData> {
        return try {
            val userDataDto = preferencesDataSource.loadUserData()
            DataResult.Success(UserDataMapper.toDomain(userDataDto))
        } catch (e: Exception) {
            DataResult.Error("Failed to load user data", e)
        }
    }

    override fun observeUserData(): Flow<UserData> {
        return preferencesDataSource.userDataFlow.map { UserDataMapper.toDomain(it) }
    }

    override suspend fun saveUserName(name: String): DataResult<Unit> {
        return try {
            val currentData = preferencesDataSource.loadUserData()
            val updatedData = currentData.copy(name = name)
            preferencesDataSource.saveUserData(updatedData)
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error("Failed to save user name", e)
        }
    }

    override suspend fun toggleSavedPhrase(phraseId: String): DataResult<Unit> {
        return try {
            val currentData = preferencesDataSource.loadUserData()
            val savedPhrases = currentData.savedPhrases.toMutableList()

            if (savedPhrases.contains(phraseId)) {
                savedPhrases.remove(phraseId)
            } else {
                savedPhrases.add(phraseId)
            }

            val updatedData = currentData.copy(savedPhrases = savedPhrases)
            preferencesDataSource.saveUserData(updatedData)
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error("Failed to toggle saved phrase", e)
        }
    }

    override suspend fun markDialogCompleted(dialogId: String, xpReward: Int): DataResult<Unit> {
        return try {
            val currentData = preferencesDataSource.loadUserData()

            if (currentData.completedDialogs.contains(dialogId)) {
                return DataResult.Success(Unit)
            }

            val completedDialogs = currentData.completedDialogs + dialogId
            val newXp = currentData.xp + xpReward

            val updatedData = currentData.copy(
                completedDialogs = completedDialogs,
                xp = newXp
            )
            preferencesDataSource.saveUserData(updatedData)
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error("Failed to mark dialog as completed", e)
        }
    }

    override suspend fun setNativeLanguage(language: Language): DataResult<Unit> {
        return try {
            val currentData = preferencesDataSource.loadUserData()
            val updatedData = currentData.copy(nativeLanguage = language.code)
            preferencesDataSource.saveUserData(updatedData)
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error("Failed to set native language", e)
        }
    }

    override suspend fun setTargetLanguage(language: Language): DataResult<Unit> {
        return try {
            val currentData = preferencesDataSource.loadUserData()
            val updatedData = currentData.copy(targetLanguage = language.code)
            preferencesDataSource.saveUserData(updatedData)
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error("Failed to set target language", e)
        }
    }

    override suspend fun getNativeLanguage(): Language {
        val userData = preferencesDataSource.loadUserData()
        return Language.fromCode(userData.nativeLanguage) ?: Language.PORTUGUESE
    }

    override suspend fun getTargetLanguage(): Language {
        val userData = preferencesDataSource.loadUserData()
        return Language.fromCode(userData.targetLanguage) ?: Language.ENGLISH
    }

    // NEW: Onboarding methods
    override suspend fun completeOnboarding(): DataResult<Unit> {
        return try {
            val currentData = preferencesDataSource.loadUserData()
            val updatedData = currentData.copy(hasCompletedOnboarding = true)
            preferencesDataSource.saveUserData(updatedData)
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error("Failed to complete onboarding", e)
        }
    }

    override suspend fun hasCompletedOnboarding(): Boolean {
        val userData = preferencesDataSource.loadUserData()
        return userData.hasCompletedOnboarding
    }
}