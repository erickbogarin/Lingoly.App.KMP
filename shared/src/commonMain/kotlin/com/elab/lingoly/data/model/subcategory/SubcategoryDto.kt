package com.elab.lingoly.data.model.subcategory

import com.elab.lingoly.data.model.dialog.DialogDto
import kotlinx.serialization.Serializable

@Serializable
data class SubcategoryDto(
    val id: String,
    val name: Map<String, String>,
    val dialogs: Map<String, DialogDto>
)
