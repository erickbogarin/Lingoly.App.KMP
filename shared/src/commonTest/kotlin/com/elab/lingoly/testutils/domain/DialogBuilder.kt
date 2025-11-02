package com.elab.lingoly.testutils.domain

import com.elab.lingoly.domain.model.CommonRoles
import com.elab.lingoly.domain.model.Dialog
import com.elab.lingoly.domain.model.DialogMeta
import com.elab.lingoly.domain.model.Language
import com.elab.lingoly.domain.model.Phrase
import com.elab.lingoly.domain.model.Translation

class DialogBuilder {
    var id: String = "d1"
    var categoryId: String = "default_category"
    var subcategoryId: String = "default_subcategory"
    private var titleEn: String = "Default Title"
    private var titlePt: String = "Título Padrão"
    private val tags: MutableList<String> = mutableListOf()
    private val phrases: MutableList<Phrase> = mutableListOf()
    private var meta: DialogMeta = DialogMeta(
        xpReward = 10,
        estimatedTime = "30",
        difficulty = "easy",
        targetLanguage = Language.ENGLISH
    )

    fun title(en: String, pt: String? = null) {
        titleEn = en
        titlePt = pt ?: en
    }

    fun tags(vararg values: String) {
        tags.clear()
        tags.addAll(values)
    }

    fun phrase(
        text: String,
        role: String = CommonRoles.GENERIC,
        id: String = "p${phrases.size + 1}",
        tip: String? = null,
        dialogId: String = this.id
    ) {
        phrases += Phrase(
            id = id,
            dialogId = dialogId,
            role = role,
            translations = Translation(mapOf(Language.ENGLISH to text)),
            tips = tip?.let { Translation(mapOf(Language.ENGLISH to it)) } ?: Translation(emptyMap())
        )
    }

    fun meta(
        xpReward: Int = 10,
        estimatedTime: String = "30",
        difficulty: String = "easy",
        targetLanguage: Language = Language.ENGLISH
    ) {
        meta = DialogMeta(xpReward, estimatedTime, difficulty, targetLanguage)
    }

    fun build(): Dialog = Dialog(
        id = id,
        title = Translation(
            mapOf(
                Language.ENGLISH to titleEn,
                Language.PORTUGUESE to titlePt
            )
        ),
        categoryId = categoryId,
        subcategoryId = subcategoryId,
        tags = tags.ifEmpty { listOf("default") },
        phrases = phrases.toList(),
        meta = meta
    )
}
