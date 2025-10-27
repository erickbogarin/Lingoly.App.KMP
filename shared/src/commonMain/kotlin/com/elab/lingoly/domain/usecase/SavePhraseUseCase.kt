package com.elab.lingoly.domain.usecase

import com.elab.lingoly.domain.repository.UserRepository
import com.elab.lingoly.utils.DataResult

class SavePhraseUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(phraseId: String): DataResult<Unit> {
        return userRepository.toggleSavedPhrase(phraseId)
    }
}