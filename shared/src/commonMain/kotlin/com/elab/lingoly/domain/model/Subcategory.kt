package com.elab.lingoly.domain.model

data class Subcategory(
    val id: String,
    val name: Translation,
    val categoryId: String,
    val dialogs: List<Dialog>
) {
    fun getName(language: Language): String {
        return name.getOrDefault(language)
    }
}
