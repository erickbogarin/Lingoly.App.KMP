package com.elab.lingoly.domain.model

data class Dialog(
    val id: String,
    val title: Translation,
    val categoryId: String,
    val subcategoryId: String,
    val phrases: List<Phrase>,
    val meta: DialogMeta
) {
    fun getTitle(language: Language): String {
        return title.getOrDefault(language)
    }
}
