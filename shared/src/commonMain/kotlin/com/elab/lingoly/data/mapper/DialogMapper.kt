package com.elab.lingoly.data.mapper

import com.elab.lingoly.data.model.category.CategoryDto
import com.elab.lingoly.data.model.dialog.DialogDto
import com.elab.lingoly.data.model.dialog.DialogMetaDto
import com.elab.lingoly.data.model.phrase.PhraseDto
import com.elab.lingoly.data.model.subcategory.SubcategoryDto
import com.elab.lingoly.domain.model.Category
import com.elab.lingoly.domain.model.Dialog
import com.elab.lingoly.domain.model.DialogMeta
import com.elab.lingoly.domain.model.Language
import com.elab.lingoly.domain.model.Phrase
import com.elab.lingoly.domain.model.Subcategory
import com.elab.lingoly.domain.model.Translation

object DialogMapper {

    private fun mapLanguageKeys(input: Map<String, String>): Map<Language, String> {
        val out = mutableMapOf<Language, String>()
        for ((code, text) in input) {
            val lang = Language.fromCode(code) ?: Language.ENGLISH
            out[lang] = text
        }
        return out
    }

    fun toDomain(dto: CategoryDto): Category {
        return Category(
            id = dto.id,
            name = Translation(translations = mapLanguageKeys(dto.name)),
            subcategories = dto.subcategories.map { toDomain(it, dto.id) }
        )
    }

    fun toDomain(dto: SubcategoryDto, categoryId: String): Subcategory {
        return Subcategory(
            id = dto.id,
            name = Translation(translations = mapLanguageKeys(dto.name)),
            categoryId = categoryId,
            dialogs = dto.dialogs.map { toDomain(it, categoryId, dto.id) }
        )
    }

    fun toDomain(dto: DialogDto, categoryId: String, subcategoryId: String): Dialog {
        return Dialog(
            id = dto.id,
            title = Translation(translations = mapLanguageKeys(dto.title)),
            categoryId = categoryId,
            subcategoryId = subcategoryId,
            tags = dto.tags,
            phrases = dto.phrases.map { toDomain(it, dto.id) },
            meta = toDomain(dto.meta)
        )
    }

    fun toDomain(dto: PhraseDto, dialogId: String): Phrase {
        return Phrase(
            id = dto.id,
            dialogId = dialogId,
            role = dto.role,
            translations = Translation(translations = mapLanguageKeys(dto.translations)),
            tips = Translation(translations = mapLanguageKeys(dto.tips))
        )
    }

    fun toDomain(dto: DialogMetaDto): DialogMeta {
        return DialogMeta(
            xpReward = dto.xpReward,
            estimatedTime = dto.estimatedTime,
            difficulty = dto.difficulty,
            targetLanguage = Language.fromCode(dto.targetLanguage) ?: Language.ENGLISH
        )
    }
}
