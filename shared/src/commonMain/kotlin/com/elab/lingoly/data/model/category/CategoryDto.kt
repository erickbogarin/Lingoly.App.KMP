package com.elab.lingoly.data.model.category

import com.elab.lingoly.data.model.subcategory.SubcategoryDto
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: String,
    val name: Map<String, String>,
    val subcategories: List<SubcategoryDto>
)