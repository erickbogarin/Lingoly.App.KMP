package com.elab.lingoly.domain.repository

import com.elab.lingoly.domain.model.Language
import com.elab.lingoly.domain.model.UserData
import com.elab.lingoly.utils.DataResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserData(): DataResult<UserData>
    fun observeUserData(): Flow<UserData>
    suspend fun saveUserName(name: String): DataResult<Unit>
    suspend fun toggleSavedPhrase(phraseId: String): DataResult<Unit>
    suspend fun markDialogCompleted(dialogId: String, xpReward: Int): DataResult<Unit>

    suspend fun setNativeLanguage(language: Language): DataResult<Unit>
    suspend fun setTargetLanguage(language: Language): DataResult<Unit>
    suspend fun getNativeLanguage(): Language
    suspend fun getTargetLanguage(): Language

    suspend fun completeOnboarding(): DataResult<Unit>
    suspend fun hasCompletedOnboarding(): Boolean
}