package com.elab.lingoly.domain.usecase

import com.elab.lingoly.domain.model.Language
import com.elab.lingoly.domain.repository.UserRepository

data class LanguagePreferences(
    val nativeLanguage: Language,
    val targetLanguage: Language
)

class GetUserLanguagePreferencesUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): LanguagePreferences {
        return LanguagePreferences(
            nativeLanguage = userRepository.getNativeLanguage(),
            targetLanguage = userRepository.getTargetLanguage()
        )
    }
}
