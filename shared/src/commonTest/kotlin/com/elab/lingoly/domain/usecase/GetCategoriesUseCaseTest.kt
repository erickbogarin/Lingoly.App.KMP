package com.elab.lingoly.domain.usecase

import com.elab.lingoly.data.local.DialogJsonDataSource
import com.elab.lingoly.data.local.LocalDialogRepository
import com.elab.lingoly.utils.DataResult
import kotlin.test.*
import kotlinx.coroutines.test.runTest

class GetCategoriesUseCaseTest {

    private val testJson = """
        {
          "categories": [
            {
              "id": "restaurant",
              "name": {"en": "Restaurant", "pt": "Restaurante"},
              "subcategories": []
            },
            {
              "id": "travel",
              "name": {"en": "Travel", "pt": "Viagem"},
              "subcategories": []
            }
          ]
        }
    """.trimIndent()

    private val dataSource = DialogJsonDataSource(testJson)
    private val repository = LocalDialogRepository(dataSource)
    private val useCase = GetCategoriesUseCase(repository)

    @Test
    fun `invoke should return all categories`() = runTest {
        val result = useCase()

        assertTrue(result is DataResult.Success)
        val categories = (result as DataResult.Success).data
        assertEquals(2, categories.size)
        assertEquals("restaurant", categories[0].id)
        assertEquals("travel", categories[1].id)
    }

    @Test
    fun `invoke should return error when repository fails`() = runTest {
        val invalidJson = "{ invalid json }"
        val invalidDataSource = DialogJsonDataSource(invalidJson)
        val invalidRepository = LocalDialogRepository(invalidDataSource)
        val invalidUseCase = GetCategoriesUseCase(invalidRepository)

        val result = invalidUseCase()

        assertTrue(result is DataResult.Error)
    }
}

