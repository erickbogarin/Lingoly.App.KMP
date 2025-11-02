package com.elab.lingoly.data.mapper

import com.elab.lingoly.domain.model.Language
import com.elab.lingoly.testutils.*
import kotlin.test.*

class DialogMapperTest {

    @Test
    fun `toDomain should map CategoryDto correctly`() {
        val dto = categoryDto {
            id = "restaurant"
            name("en" to "Restaurant", "pt" to "Restaurante")
        }

        val domain = DialogMapper.toDomain(dto)

        assertEquals("restaurant", domain.id)
        assertEquals("Restaurant", domain.name.get(Language.ENGLISH))
        assertEquals("Restaurante", domain.name.get(Language.PORTUGUESE))
        assertTrue(domain.subcategories.isEmpty())
    }

    @Test
    fun `toDomain should map SubcategoryDto correctly`() {
        val dto = subcategoryDto {
            id = "ordering_food"
            categoryId = "restaurant"
            name("en" to "Ordering Food", "pt" to "Fazendo Pedido")
        }

        val domain = DialogMapper.toDomain(dto, "restaurant")

        assertEquals("ordering_food", domain.id)
        assertEquals("restaurant", domain.categoryId)
        assertEquals("Ordering Food", domain.name.get(Language.ENGLISH))
        assertEquals("Fazendo Pedido", domain.name.get(Language.PORTUGUESE))
        assertTrue(domain.dialogs.isEmpty())
    }

    @Test
    fun `toDomain should map DialogDto correctly`() {
        val dto = dialogDto {
            id = "d1"
            title("en" to "Dialog Title", "pt" to "Título do Diálogo")
            tags("restaurant", "ordering")
            meta {
                xpReward = 10
                estimatedTime = "45"
                difficulty = "easy"
                targetLanguage = "en"
            }
            phrase {
                id = "p1"
                role = "CUSTOMER"
                translations("en" to "Hello", "pt" to "Olá")
                tips("en" to "Tip", "pt" to "Dica")
            }
        }

        val domain = DialogMapper.toDomain(dto, "restaurant", "ordering_food")

        assertEquals("d1", domain.id)
        assertEquals("restaurant", domain.categoryId)
        assertEquals("ordering_food", domain.subcategoryId)
        assertEquals("Dialog Title", domain.title.get(Language.ENGLISH))
        assertEquals(2, domain.tags.size)
        assertEquals(1, domain.phrases.size)
        assertEquals(10, domain.meta.xpReward)
    }

    @Test
    fun `toDomain should map PhraseDto correctly`() {
        val dto = phraseDto {
            id = "p1"
            role = "CUSTOMER"
            translations("en" to "Hello", "pt" to "Olá")
            tips("en" to "Greeting", "pt" to "Saudação")
        }

        val domain = DialogMapper.toDomain(dto, "d1")

        assertEquals("p1", domain.id)
        assertEquals("d1", domain.dialogId)
        assertEquals("CUSTOMER", domain.role)
        assertEquals("Hello", domain.getText(Language.ENGLISH))
        assertEquals("Olá", domain.getText(Language.PORTUGUESE))
        assertEquals("Greeting", domain.getTip(Language.ENGLISH))
    }

    @Test
    fun `toDomain should map DialogMetaDto correctly`() {
        val dto = dialogMetaDto {
            xpReward = 15
            estimatedTime = "60"
            difficulty = "medium"
            targetLanguage = "pt"
        }

        val domain = DialogMapper.toDomain(dto)

        assertEquals(15, domain.xpReward)
        assertEquals("60", domain.estimatedTime)
        assertEquals("medium", domain.difficulty)
        assertEquals(Language.PORTUGUESE, domain.targetLanguage)
    }

    @Test
    fun `toDomain should handle missing language codes gracefully`() {
        val dto = phraseDto {
            id = "p1"
            role = "GENERIC"
            translations("xx" to "Unknown", "en" to "Hello")
            tips(emptyMap())
        }

        val domain = DialogMapper.toDomain(dto, "d1")

        assertNotNull(domain.translations.get(Language.ENGLISH))
        assertEquals("Hello", domain.getText(Language.ENGLISH))
    }

    @Test
    fun `toDomain should preserve phrase role exactly as provided`() {
        val dto = phraseDto {
            id = "p1"
            role = "CUSTOM_ROLE"
            translations("en" to "Text")
        }

        val domain = DialogMapper.toDomain(dto, "d1")

        assertEquals("CUSTOM_ROLE", domain.role)
    }

    @Test
    fun `toDomain should handle empty tips in PhraseDto`() {
        val dto = phraseDto {
            id = "p1"
            role = "WAITER"
            translations("en" to "Would you like?")
            tips(emptyMap())
        }

        val domain = DialogMapper.toDomain(dto, "d1")

        assertTrue(domain.tips.translations.isEmpty())
        assertEquals("", domain.getTip(Language.ENGLISH))
    }

    @Test
    fun `toDomain should map full nested structure correctly`() {
        val dto = categoryDto {
            id = "restaurant"
            name("en" to "Restaurant")
            subcategory {
                id = "ordering"
                name("en" to "Ordering")
                dialog {
                    id = "d1"
                    title("en" to "Dialog")
                    phrase {
                        id = "p1"
                        role = "CUSTOMER"
                        translations("en" to "Hi")
                    }
                }
            }
        }

        val domain = DialogMapper.toDomain(dto)
        val dialog = domain.subcategories.first().dialogs.first()
        val phrase = dialog.phrases.first()

        assertEquals("restaurant", domain.id)
        assertEquals("ordering", dialog.subcategoryId)
        assertEquals("Hi", phrase.getText(Language.ENGLISH))
    }

    @Test
    fun `toDomain should fallback to ENGLISH if targetLanguage invalid`() {
        val dto = dialogMetaDto {
            targetLanguage = "xx"
        }

        val domain = DialogMapper.toDomain(dto)
        assertEquals(Language.ENGLISH, domain.targetLanguage)
    }

    @Test
    fun `toDomain should handle missing name translations gracefully`() {
        val dto = categoryDto {
            id = "restaurant"
            // intentionally no names
        }

        val domain = DialogMapper.toDomain(dto)
        assertTrue(domain.name.translations.isEmpty())
    }

    @Test
    fun `toDomain should map multiple phrases and tags correctly`() {
        val dto = dialogDto {
            id = "d1"
            tags("a", "b", "c")
            phrase { id = "p1"; translations("en" to "Hi") }
            phrase { id = "p2"; translations("en" to "Bye") }
        }

        val domain = DialogMapper.toDomain(dto, "cat", "sub")

        assertEquals(3, domain.tags.size)
        assertEquals(2, domain.phrases.size)
    }
}
