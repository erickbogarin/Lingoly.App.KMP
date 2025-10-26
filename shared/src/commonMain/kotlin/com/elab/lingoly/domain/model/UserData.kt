package com.elab.lingoly.domain.model

data class UserData(
    val name: String,
    val completedDialogs: List<String>,
    val savedPhrases: List<String>,
    val xp: Int,
    val streak: Int,
    val achievements: List<String>,
    val nativeLanguage: Language = Language.PORTUGUESE,
    val targetLanguage: Language = Language.ENGLISH,
    val hasCompletedOnboarding: Boolean = false // NEW: Ready for Sprint 2
)