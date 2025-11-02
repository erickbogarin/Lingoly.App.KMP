package com.elab.lingoly.domain.model

import com.elab.lingoly.testutils.dialog
import kotlin.test.*

class DialogTest {

    private val dialog = dialog {
        id = "d1"
        title("Ordering Food", "Fazendo Pedido")
        tags("food", "restaurant", "ordering")

        phrase("Hello", role = "CUSTOMER")
        phrase("Welcome", role = "WAITER")
    }

    @Test
    fun `getTitle should return correct title for language`() {
        assertEquals("Ordering Food", dialog.getTitle(Language.ENGLISH))
        assertEquals("Fazendo Pedido", dialog.getTitle(Language.PORTUGUESE))
    }

    @Test
    fun `hasTag should return true for existing tag`() {
        assertTrue(dialog.hasTag("food"))
        assertTrue(dialog.hasTag("FOOD"))
    }

    @Test
    fun `getRoleDistribution should handle multiple phrases with same role`() {
        val dialogWithRepeatedRole = dialog {
            id = "d2"
            title("Ordering Food")
            phrase("Hello", role = "CUSTOMER")
            phrase("Welcome", role = "WAITER")
            phrase("Thanks", role = "CUSTOMER")
        }

        val dist = dialogWithRepeatedRole.getRoleDistribution()
        assertEquals(2, dist["CUSTOMER"])
        assertEquals(1, dist["WAITER"])
    }

    @Test
    fun `getTitle should fallback to English if translation missing`() {
        val dialog = dialog {
            title("Only English")
        }
        assertEquals("Only English", dialog.getTitle(Language.SPANISH))
    }

    @Test
    fun `getRoles should return unique roles from phrases`() {
        val roles = dialog.getRoles()
        assertEquals(setOf("CUSTOMER", "WAITER"), roles.toSet())
    }

    @Test
    fun `getPhrasesByRole should return phrases matching role ignoring case`() {
        val phrases = dialog.getPhrasesByRole("waiter")
        assertEquals(1, phrases.size)
        assertEquals("Welcome", phrases.first().getText(Language.ENGLISH))
    }

    @Test
    fun `hasAllTags should return true only if all provided tags exist`() {
        assertTrue(dialog.hasAllTags(listOf("food", "ordering")))
        assertFalse(dialog.hasAllTags(listOf("food", "missing")))
    }
}
