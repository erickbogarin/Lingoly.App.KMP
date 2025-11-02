package com.elab.lingoly.testutils.data

import com.elab.lingoly.data.model.dialog.DialogDto
import com.elab.lingoly.data.model.dialog.DialogMetaDto
import com.elab.lingoly.data.model.phrase.PhraseDto

class DialogDtoBuilder {
    var id: String = "dialog"
    private val titles = mutableMapOf<String, String>()
    private val tags = mutableListOf<String>()
    private val phrases = mutableListOf<PhraseDto>()
    private var meta: DialogMetaDto = DialogMetaDto(0, "", "", "en")

    fun title(vararg entries: Pair<String, String>) = apply { titles.putAll(entries) }
    fun tags(vararg values: String) = apply { tags += values }
    fun phrase(block: PhraseDtoBuilder.() -> Unit) = apply { phrases += PhraseDtoBuilder().apply(block).build() }
    fun meta(block: DialogMetaDtoBuilder.() -> Unit) = apply { meta = DialogMetaDtoBuilder().apply(block).build() }

    fun build() = DialogDto(id, titles, tags, phrases, meta)
}