package com.elab.lingoly.testutils.data

import com.elab.lingoly.data.model.category.CategoryDto
import com.elab.lingoly.data.model.subcategory.SubcategoryDto

class CategoryDtoBuilder {
    var id: String = "category"
    private val names = mutableMapOf<String, String>()
    private val subs = mutableListOf<SubcategoryDto>()

    fun name(vararg entries: Pair<String, String>) = apply { names.putAll(entries) }
    fun subcategory(block: SubcategoryDtoBuilder.() -> Unit) = apply { subs += SubcategoryDtoBuilder().apply(block).build() }

    fun build() = CategoryDto(id, names, subs)
}