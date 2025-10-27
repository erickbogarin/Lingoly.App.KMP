package com.elab.lingoly.domain.usecase

import com.elab.lingoly.domain.model.Language
import com.elab.lingoly.domain.repository.UserRepository
import com.elab.lingoly.utils.DataResult

class SetupLanguagePreferencesUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        nativeLanguage: Language,
        targetLanguage: Language
    ): DataResult<Unit> {
        if (nativeLanguage == targetLanguage) {
            return DataResult.Error("Native and target languages must be different")
        }

        return try {
            val nativeResult = userRepository.setNativeLanguage(nativeLanguage)
            if (nativeResult is DataResult.Error) return nativeResult

            val targetResult = userRepository.setTargetLanguage(targetLanguage)
            if (targetResult is DataResult.Error) return targetResult

            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Error("Failed to setup language preferences", e)
        }
    }
}