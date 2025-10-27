package com.elab.lingoly.domain.usecase

import com.elab.lingoly.domain.repository.UserRepository
import com.elab.lingoly.utils.DataResult

class CheckOnboardingStatusUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): DataResult<Boolean> {
        return when (val result = userRepository.getUserData()) {
            is DataResult.Success -> DataResult.Success(result.data.hasCompletedOnboarding)
            is DataResult.Error -> result
            else -> DataResult.Error("Unknown error checking onboarding status")
        }
    }
}