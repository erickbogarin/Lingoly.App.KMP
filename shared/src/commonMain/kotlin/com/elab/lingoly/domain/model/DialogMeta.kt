package com.elab.lingoly.domain.model

data class DialogMeta(
    val xpReward: Int,
    val estimatedTime: String,
    val difficulty: String,
    val targetLanguage: Language = Language.ENGLISH
)
