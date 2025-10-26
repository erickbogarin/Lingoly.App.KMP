package com.elab.lingoly.domain.model

data class Category (
    val id: String,
    val name: Translation,
    val subcategories: List<Subcategory>
)
