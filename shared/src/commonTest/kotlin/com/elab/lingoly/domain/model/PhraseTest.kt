package com.elab.lingoly.domain.model

import kotlin.test.*

class PhraseTest {

    private val phrase = Phrase(
        id = "p1",
        dialogId = "d1",
        translations = Translation(
            mapOf(
                Language.ENGLISH to "My phone is out of juice.",
                Language.PORTUGUESE to "Meu celular está sem bateria."
            )
        ),
        tips = Translation(
            mapOf(
                Language.ENGLISH to "'Out of juice' means the battery is dead.",
                Language.PORTUGUESE to "'Out of juice' quer dizer que a bateria acabou."
            )
        )
    )

    @Test
    fun `should return correct text for given language`() {
        val text = phrase.getText(Language.ENGLISH)
        assertEquals("My phone is out of juice.", text)
    }

    @Test
    fun `should return correct tip for given language`() {
        val tip = phrase.getTip(Language.PORTUGUESE)
        assertEquals("'Out of juice' quer dizer que a bateria acabou.", tip)
    }

    @Test
    fun `should return true if translation exists for given language`() {
        assertTrue(phrase.hasTranslation(Language.ENGLISH))
    }

    @Test
    fun `should return false if translation does not exist for given language`() {
        assertFalse(phrase.hasTranslation(Language.SPANISH))
    }

    @Test
    fun `should return true if tips exist for given language`() {
        assertTrue(phrase.hasTips(Language.PORTUGUESE))
    }

    @Test
    fun `should return false if tips do not exist for given language`() {
        assertFalse(phrase.hasTips(Language.SPANISH))
    }

    @Test
    fun `should return all available languages`() {
        val availableLanguages = phrase.getAvailableLanguages()
        assertEquals(setOf(Language.ENGLISH, Language.PORTUGUESE), availableLanguages)
    }
}