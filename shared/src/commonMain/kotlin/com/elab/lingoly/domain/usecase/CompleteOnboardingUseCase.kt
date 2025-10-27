package com.elab.lingoly.domain.usecase

import com.elab.lingoly.domain.repository.UserRepository
import com.elab.lingoly.utils.DataResult

class CompleteOnboardingUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): DataResult<Unit> {
        return userRepository.completeOnboarding()
    }
}