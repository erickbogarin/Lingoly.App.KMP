package com.elab.lingoly.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferencesDto(
    val showTips: Boolean = true,
    val autoPlayAudio: Boolean = false,
    val dailyGoalMinutes: Int = 15
)