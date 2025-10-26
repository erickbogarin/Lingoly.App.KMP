package com.elab.lingoly.data.model.dialog

import com.elab.lingoly.data.model.phrase.PhraseDto
import kotlinx.serialization.Serializable

@Serializable
data class DialogDto(
    val id: String,
    val title: Map<String, String>,
    val tags: List<String> = emptyList(),
    val phrases: List<PhraseDto>,
    val meta: DialogMetaDto
)