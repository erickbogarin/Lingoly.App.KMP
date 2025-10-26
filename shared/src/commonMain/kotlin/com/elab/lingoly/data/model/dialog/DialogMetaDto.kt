package com.elab.lingoly.data.model.dialog

import kotlinx.serialization.Serializable

@Serializable
data class DialogMetaDto(
    val xpReward: Int,
    val estimatedTime: String,
    val difficulty: String,
    val targetLanguage: String = "en"
)