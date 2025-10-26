package com.elab.lingoly.data.model.phrase

import kotlinx.serialization.Serializable

@Serializable
data class PhraseDto(
    val id: String,
    val translations: Map<String, String>,
    val tips: Map<String, String> = emptyMap(),
)