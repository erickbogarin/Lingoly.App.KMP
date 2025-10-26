package com.elab.lingoly.data.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDataDto(
    val name: String = "",
    val completedDialogs: List<String> = emptyList(),
    val savedPhrases: List<String> = emptyList(),
    val xp: Int = 0,
    val streak: Int = 0,
    val achievements: List<String> = emptyList(),
    val nativeLanguage: String = "pt",
    val targetLanguage: String = "en"
)