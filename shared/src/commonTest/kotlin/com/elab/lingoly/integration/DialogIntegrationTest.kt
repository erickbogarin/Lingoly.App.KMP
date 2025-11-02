package com.elab.lingoly.integration

import com.elab.lingoly.data.local.DialogJsonDataSource
import com.elab.lingoly.data.local.LocalDialogRepository
import com.elab.lingoly.domain.model.Language
import com.elab.lingoly.domain.usecase.GetCategoriesUseCase
import com.elab.lingoly.domain.usecase.GetDialogUseCase
import com.elab.lingoly.domain.usecase.GetDialogsUseCase
import com.elab.lingoly.domain.usecase.GetSubcategoriesUseCase
import com.elab.lingoly.utils.DataResult
import kotlinx.coroutines.test.runTest
import kotlin.test.*

/**
 * Integration test that validates the complete flow:
 * JSON → DataSource → Repository → UseCase → Domain Model
 */
class DialogIntegrationTest {

    private lateinit var dataSource: DialogJsonDataSource
    private lateinit var repository: LocalDialogRepository
    private lateinit var getCategoriesUseCase: GetCategoriesUseCase
    private lateinit var getSubcategoriesUseCase: GetSubcategoriesUseCase
    private lateinit var getDialogsUseCase: GetDialogsUseCase
    private lateinit var getDialogUseCase: GetDialogUseCase

    @BeforeTest
    fun setup() {
        dataSource = DialogJsonDataSource(DialogFixtures.completeJson)
        repository = LocalDialogRepository(dataSource)
        getCategoriesUseCase = GetCategoriesUseCase(repository)
        getSubcategoriesUseCase = GetSubcategoriesUseCase(repository)
        getDialogsUseCase = GetDialogsUseCase(repository)
        getDialogUseCase = GetDialogUseCase(repository)
    }

    @Test
    fun `complete user flow should work end-to-end`() = runTest {
        val categoriesResult = getCategoriesUseCase()
        assertTrue(categoriesResult is DataResult.Success)
        val categories = (categoriesResult as DataResult.Success).data
        assertEquals(1, categories.size)

        val category = categories.first()
        assertEquals("restaurant", category.id)
        assertEquals("Restaurant", category.name.get(Language.ENGLISH))

        val subcategoriesResult = getSubcategoriesUseCase(category.id)
        assertTrue(subcategoriesResult is DataResult.Success)
        val subcategories = (subcategoriesResult as DataResult.Success).data
        val subcategory = subcategories.first()
        assertEquals("ordering_food", subcategory.id)

        val dialogsResult = getDialogsUseCase(category.id, subcategory.id)
        assertTrue(dialogsResult is DataResult.Success)
        val dialogs = (dialogsResult as DataResult.Success).data
        val dialogFromList = dialogs.first()
        assertEquals("salmon_order", dialogFromList.id)

        val dialogResult = getDialogUseCase(dialogFromList.id)
        assertTrue(dialogResult is DataResult.Success)
        val dialog = (dialogResult as DataResult.Success).data

        assertEquals("salmon_order", dialog.id)
        assertEquals("Ordering Salmon", dialog.getTitle(Language.ENGLISH))
        assertEquals("Pedindo Salmão", dialog.getTitle(Language.PORTUGUESE))
        assertEquals(3, dialog.tags.size)
        assertEquals(2, dialog.phrases.size)

        assertEquals(10, dialog.meta.xpReward)
        assertEquals("45", dialog.meta.estimatedTime)
        assertEquals("easy", dialog.meta.difficulty)
        assertEquals(Language.ENGLISH, dialog.meta.targetLanguage)

        val phrase1 = dialog.phrases[0]
        assertEquals("p1", phrase1.id)
        assertEquals("CUSTOMER", phrase1.role)
        assertEquals("I'd like the grilled salmon, please.", phrase1.getText(Language.ENGLISH))
        assertEquals("Polite way to order", phrase1.getTip(Language.ENGLISH))

        val phrase2 = dialog.phrases[1]
        assertEquals("p2", phrase2.id)
        assertEquals("WAITER", phrase2.role)
        assertEquals("Would you like any sides?", phrase2.getText(Language.ENGLISH))

        assertTrue(dialog.hasTag("food"))
        assertTrue(dialog.hasTag("restaurant"))
        assertFalse(dialog.hasTag("travel"))

        val customerPhrases = dialog.getPhrasesByRole("CUSTOMER")
        assertEquals(1, customerPhrases.size)

        val roles = dialog.getRoles()
        assertEquals(setOf("CUSTOMER", "WAITER"), roles.toSet())
    }

    @Test
    fun `JSON parsing should handle all data types correctly`() = runTest {
        val dialogResult = getDialogUseCase("salmon_order")
        assertTrue(dialogResult is DataResult.Success)

        val dialog = (dialogResult as DataResult.Success).data

        assertNotNull(dialog.title.get(Language.ENGLISH))
        assertNotNull(dialog.title.get(Language.PORTUGUESE))
        assertNull(dialog.title.get(Language.SPANISH))

        assertTrue(dialog.tags.isNotEmpty())
        assertTrue(dialog.tags.all { it is String })

        assertTrue(dialog.meta.xpReward > 0)
        assertTrue(dialog.meta.estimatedTime.isNotBlank())
        assertTrue(dialog.meta.difficulty in listOf("easy", "medium", "hard"))

        dialog.phrases.forEach { phrase ->
            assertNotNull(phrase.translations.get(Language.ENGLISH))
            assertNotNull(phrase.translations.get(Language.PORTUGUESE))
            assertNotNull(phrase.tips)
        }
    }

    @Test
    fun `data should remain consistent across multiple accesses`() = runTest {
        val result1 = getDialogUseCase("salmon_order")
        val result2 = getDialogUseCase("salmon_order")
        val result3 = getDialogUseCase("salmon_order")

        assertTrue(result1 is DataResult.Success)
        assertTrue(result2 is DataResult.Success)
        assertTrue(result3 is DataResult.Success)

        val dialog1 = (result1 as DataResult.Success).data
        val dialog2 = (result2 as DataResult.Success).data
        val dialog3 = (result3 as DataResult.Success).data

        assertEquals(dialog1.id, dialog2.id)
        assertEquals(dialog2.id, dialog3.id)
        assertEquals(dialog1.phrases.size, dialog2.phrases.size)
        assertEquals(dialog2.phrases.size, dialog3.phrases.size)
    }
}
