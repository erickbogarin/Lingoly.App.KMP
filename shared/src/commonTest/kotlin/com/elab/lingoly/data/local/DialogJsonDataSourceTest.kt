package com.elab.lingoly.data.local

import kotlin.test.*

class DialogJsonDataSourceTest {

    @Test
    fun `dialogData should parse valid JSON successfully`() {
        val dataSource = DialogJsonDataSource(DialogJsonFixtures.valid)

        val data = dataSource.dialogData

        assertEquals(2, data.categories.size)
        assertEquals("restaurant", data.categories[0].id)
    }

    @Test
    fun `dialogData should be cached after first access`() {
        val dataSource = DialogJsonDataSource(DialogJsonFixtures.valid)

        assertFalse(dataSource.isCached())

        val data1 = dataSource.dialogData
        assertTrue(dataSource.isCached())

        val data2 = dataSource.dialogData
        assertSame(data1, data2) // Should return same cached instance
    }

    @Test
    fun `dialogData should throw JsonParsingException on invalid JSON`() {
        val dataSource = DialogJsonDataSource(DialogJsonFixtures.invalid)

        assertFailsWith<JsonParsingException> {
            dataSource.dialogData
        }
    }

    @Test
    fun `clearCache should return new instance with cleared cache`() {
        val dataSource1 = DialogJsonDataSource(DialogJsonFixtures.valid)

        dataSource1.dialogData
        assertTrue(dataSource1.isCached())

        val dataSource2 = dataSource1.clearCache()

        assertTrue(dataSource1.isCached())
        assertFalse(dataSource2.isCached())
        assertNotSame(dataSource1, dataSource2)
    }

    @Test
    fun `dialogData should parse nested structure correctly`() {
        val dataSource = DialogJsonDataSource(DialogJsonFixtures.valid)

        val data = dataSource.dialogData
        val category = data.categories.first()
        val subcategory = category.subcategories.first()
        val dialog = subcategory.dialogs.first()

        assertEquals("ordering", subcategory.id)
        assertEquals("d1", dialog.id)
        assertEquals(1, dialog.phrases.size)
        assertEquals("p1", dialog.phrases.first().id)
    }

    @Test
    fun `dialogData should handle missing optional fields`() {
        val dataSource = DialogJsonDataSource(DialogJsonFixtures.minimal)

        val data = dataSource.dialogData
        val category = data.categories.first()

        assertEquals("test", category.id)
        assertTrue(category.subcategories.isEmpty())
    }

    @Test
    fun `dialogData should handle empty categories list`() {
        val dataSource = DialogJsonDataSource(DialogJsonFixtures.empty)
        val data = dataSource.dialogData

        assertTrue(data.categories.isEmpty())
    }

    @Test
    fun `dialogData should ignore unknown fields`() {
        val dataSource = DialogJsonDataSource(DialogJsonFixtures.withUnknownFields)

        val data = dataSource.dialogData

        assertEquals("restaurant", data.categories.first().id)
        assertTrue(data.categories.first().subcategories.isEmpty())
    }
}
