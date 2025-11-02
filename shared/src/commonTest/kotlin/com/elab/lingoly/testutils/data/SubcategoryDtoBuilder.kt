package com.elab.lingoly.testutils.data

import com.elab.lingoly.data.model.dialog.DialogDto
import com.elab.lingoly.data.model.subcategory.SubcategoryDto

class SubcategoryDtoBuilder {
    var id: String = "subcategory"
    var categoryId: String = ""
    private val names = mutableMapOf<String, String>()
    private val dialogs = mutableListOf<DialogDto>()

    fun name(vararg entries: Pair<String, String>) = apply { names.putAll(entries) }
    fun dialog(block: DialogDtoBuilder.() -> Unit) = apply { dialogs += DialogDtoBuilder().apply(block).build() }

    fun build() = SubcategoryDto(id, names, dialogs)
}