package com.elab.lingoly.testutils.data

import com.elab.lingoly.data.model.phrase.PhraseDto

class PhraseDtoBuilder {
    var id: String = "phrase"
    var role: String = "GENERIC"
    private val translations = mutableMapOf<String, String>()
    private val tips = mutableMapOf<String, String>()

    fun translations(vararg entries: Pair<String, String>) = apply { translations.putAll(entries) }
    fun tips(vararg entries: Pair<String, String>) = apply { tips.putAll(entries) }
    fun tips(entries: Map<String, String>) = apply { tips.putAll(entries) }

    fun build() = PhraseDto(id, role, translations, tips)
}