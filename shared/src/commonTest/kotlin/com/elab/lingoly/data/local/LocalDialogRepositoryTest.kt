package com.elab.lingoly.data.local

import com.elab.lingoly.domain.model.Language
import com.elab.lingoly.utils.DataResult
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class LocalDialogRepositoryTest {

    private val dataSource = DialogJsonDataSource(DialogJsonFixtures.valid)
    private val repository = LocalDialogRepository(dataSource)

    @Test
    fun `getCategories should return all categories`() = runTest {
        val result = repository.getCategories()

        assertTrue(result is DataResult.Success)
        val categories = result.data

        assertEquals(2, categories.size)
        assertEquals("restaurant", categories[0].id)
        assertEquals("travel", categories[1].id)
    }

    @Test
    fun `getSubcategories should return subcategories for valid category`() = runTest {
        val result = repository.getSubcategories("restaurant")

        assertTrue(result is DataResult.Success)
        val subcategories = result.data

        assertEquals(1, subcategories.size)
        assertEquals("ordering", subcategories[0].id)
    }

    @Test
    fun `getSubcategories should return error for invalid category`() = runTest {
        val result = repository.getSubcategories("invalid_id")

        assertTrue(result is DataResult.Error)
        assertTrue(result.message.contains("not found"))
    }

    @Test
    fun `getDialogsBySubcategory should return dialogs for valid subcategory`() = runTest {
        val result = repository.getDialogsBySubcategory("restaurant", "ordering")

        assertTrue(result is DataResult.Success)
        val dialogs = result.data

        assertEquals(2, dialogs.size)
        assertEquals("d1", dialogs[0].id)
        assertEquals("d2", dialogs[1].id)
    }

    @Test
    fun `getDialogsBySubcategory should return error for invalid subcategory`() = runTest {
        val result = repository.getDialogsBySubcategory("restaurant", "invalid")
        assertTrue(result is DataResult.Error)
    }

    @Test
    fun `getDialog should return specific dialog by id`() = runTest {
        val result = repository.getDialog("d1")

        assertTrue(result is DataResult.Success)
        val dialog = result.data

        assertEquals("d1", dialog.id)
        assertEquals("Order Salmon", dialog.getTitle(Language.ENGLISH))
        assertEquals(1, dialog.phrases.size)
    }

    @Test
    fun `getDialog should return error for non-existent id`() = runTest {
        val result = repository.getDialog("non_existent")

        assertTrue(result is DataResult.Error)
        assertTrue(result.message.contains("not found"))
    }

    @Test
    fun `getAllDialogs should return all dialogs from all categories`() = runTest {
        val result = repository.getAllDialogs()

        assertTrue(result is DataResult.Success)
        val dialogs = result.data

        assertEquals(2, dialogs.size)
    }

    @Test
    fun `getDialogsByDifficulty should filter by difficulty level`() = runTest {
        val result = repository.getDialogsByDifficulty("easy")

        assertTrue(result is DataResult.Success)
        val dialogs = result.data

        assertEquals(1, dialogs.size)
        assertEquals("d1", dialogs[0].id)
    }

    @Test
    fun `getDialogsByDifficulty should be case insensitive`() = runTest {
        val result = repository.getDialogsByDifficulty("EASY")

        assertTrue(result is DataResult.Success)
        val dialogs = result.data

        assertEquals(1, dialogs.size)
    }

    @Test
    fun `getDialogsByLanguage should filter by target language`() = runTest {
        val result = repository.getDialogsByLanguage("en")

        assertTrue(result is DataResult.Success)
        val dialogs = result.data

        assertEquals(2, dialogs.size)
    }

    @Test
    fun `getDialogsByLanguage should be case insensitive`() = runTest {
        val result = repository.getDialogsByLanguage("EN")

        assertTrue(result is DataResult.Success)
        val dialogs = result.data

        assertEquals(2, dialogs.size)
    }

    @Test
    fun `getDialogsByLanguage should return empty list for unmatched language`() = runTest {
        val result = repository.getDialogsByLanguage("fr")

        assertTrue(result is DataResult.Success)
        val dialogs = result.data

        assertTrue(dialogs.isEmpty())
    }
}
