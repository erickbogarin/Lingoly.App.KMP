package com.elab.lingoly.data.model.dialog

import com.elab.lingoly.data.model.phrase.PhraseDto
import kotlinx.serialization.Serializable

@Serializable
data class DialogDto(
    val id: String,
    val title: Map<String, String>,
    val phrases: Map<String, PhraseDto>,
    val meta: DialogMetaDto
)