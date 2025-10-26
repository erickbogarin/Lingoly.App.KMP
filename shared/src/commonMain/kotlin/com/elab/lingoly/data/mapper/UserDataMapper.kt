package com.elab.lingoly.data.mapper

import com.elab.lingoly.data.model.user.UserDataDto
import com.elab.lingoly.domain.model.Language
import com.elab.lingoly.domain.model.UserData

object UserDataMapper {
    fun toDomain(dto: UserDataDto): UserData {
        return UserData(
            name = dto.name,
            completedDialogs = dto.completedDialogs,
            savedPhrases = dto.savedPhrases,
            xp = dto.xp,
            streak = dto.streak,
            achievements = dto.achievements,
            nativeLanguage = Language.fromCode(dto.nativeLanguage) ?: Language.PORTUGUESE,
            targetLanguage = Language.fromCode(dto.targetLanguage) ?: Language.ENGLISH,
            hasCompletedOnboarding = dto.hasCompletedOnboarding
        )
    }

    fun toDto(domain: UserData): UserDataDto {
        return UserDataDto(
            name = domain.name,
            completedDialogs = domain.completedDialogs,
            savedPhrases = domain.savedPhrases,
            xp = domain.xp,
            streak = domain.streak,
            achievements = domain.achievements,
            nativeLanguage = domain.nativeLanguage.code,
            targetLanguage = domain.targetLanguage.code,
            hasCompletedOnboarding = domain.hasCompletedOnboarding
        )
    }
}