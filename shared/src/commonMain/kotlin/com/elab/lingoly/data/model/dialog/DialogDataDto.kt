package com.elab.lingoly.data.model.dialog

import com.elab.lingoly.data.model.category.CategoryDto
import com.elab.lingoly.data.model.user.UserDataDto
import kotlinx.serialization.Serializable

@Serializable
data class DialogDataDto (
    val categories: Map<String, CategoryDto>,
    val userData: UserDataDto,
)