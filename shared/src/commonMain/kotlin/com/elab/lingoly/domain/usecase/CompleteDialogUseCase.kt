package com.elab.lingoly.domain.usecase

import com.elab.lingoly.domain.repository.UserRepository
import com.elab.lingoly.utils.DataResult

class CompleteDialogUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(dialogId: String, xpReward: Int): DataResult<Unit> {
        return userRepository.markDialogCompleted(dialogId, xpReward)
    }
}