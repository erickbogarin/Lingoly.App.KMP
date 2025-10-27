package com.elab.lingoly.data.local

import com.elab.lingoly.data.mapper.DialogMapper
import com.elab.lingoly.data.model.category.CategoryDto
import com.elab.lingoly.domain.model.Category
import com.elab.lingoly.domain.model.Dialog
import com.elab.lingoly.domain.model.Subcategory
import com.elab.lingoly.domain.repository.DialogRepository
import com.elab.lingoly.utils.DataResult
import com.elab.lingoly.utils.DataResult.*

/**
 * Local implementation of DialogRepository.
 * Loads dialog data from JSON assets and maps to domain models.
 */
class LocalDialogRepository(
    private val jsonDataSource: DialogJsonDataSource
) : DialogRepository {

    override suspend fun getCategories(): DataResult<List<Category>> {
        return try {
            val data = jsonDataSource.dialogData
            val categories = data.categories.map { dto ->
                DialogMapper.toDomain(dto)
            }
            DataResult.Success(categories)
        } catch (e: Exception) {
            DataResult.Error("Failed to load categories", e)
        }
    }

    override suspend fun getSubcategories(categoryId: String): DataResult<List<Subcategory>> {
        return try {
            val data = jsonDataSource.dialogData
            val category = data.categories.find { it.id ==  categoryId }

            if (category != null) {
                val subcategories = category.subcategories.map { subDto ->
                    DialogMapper.toDomain(subDto, categoryId)
                }
                DataResult.Success(subcategories)
            } else {
                DataResult.Error("Category not found: $categoryId")
            }
        } catch (e: Exception) {
            DataResult.Error("Failed to load subcategories", e)
        }
    }

    override suspend fun getDialogsBySubcategory(
        categoryId: String,
        subcategoryId: String
    ): DataResult<List<Dialog>> {
        return try {
            val data = jsonDataSource.dialogData
            val category =  data.categories.find { it.id ==  categoryId }
            val subcategory = category?.subcategories?.find { it.id == subcategoryId }

            if (subcategory != null) {
                val dialogs = subcategory.dialogs.map { dialogDto ->
                    DialogMapper.toDomain(dialogDto, categoryId, subcategoryId)
                }
                DataResult.Success(dialogs)
            } else {
                DataResult.Error("Subcategory not found: $categoryId/$subcategoryId")
            }
        } catch (e: Exception) {
            DataResult.Error("Failed to load dialogs", e)
        }
    }

    override suspend fun getDialog(dialogId: String): DataResult<Dialog> {
        return try {
            val data = jsonDataSource.dialogData

            data.categories.forEach { category ->
                category.subcategories.forEach { subcategory ->
                    val dialogDto = subcategory.dialogs.find { it.id == dialogId }
                    if (dialogDto != null) {
                        val dialog = DialogMapper.toDomain(dialogDto, category.id, subcategory.id)
                        return Success(dialog)
                    }
                }
            }

            Error("Dialog not found: $dialogId")
        } catch (e: Exception) {
            Error("Failed to load dialog", e)
        }
    }

    override suspend fun getAllDialogs(): DataResult<List<Dialog>> {
        return try {
            val data = jsonDataSource.dialogData
            val allDialogs = mutableListOf<Dialog>()

            data.categories.forEach { category ->
                category.subcategories.forEach { subcategory ->
                    val dialogs = subcategory.dialogs.map { dialogDto ->
                        DialogMapper.toDomain(dialogDto, category.id, subcategory.id)
                    }
                    allDialogs.addAll(dialogs)
                }
            }

            Success(allDialogs)
        } catch (e: Exception) {
            Error("Failed to load all dialogs", e)
        }
    }

    override suspend fun getDialogsByDifficulty(difficulty: String): DataResult<List<Dialog>> {
        return try {
            when (val allDialogsResult = getAllDialogs()) {
                is DataResult.Success -> {
                    val filtered = allDialogsResult.data.filter { dialog ->
                        dialog.meta.difficulty.equals(difficulty, ignoreCase = true)
                    }
                    DataResult.Success(filtered)
                }
                is DataResult.Error -> allDialogsResult
                is DataResult.Loading -> DataResult.Loading

            }
        } catch (e: Exception) {
            DataResult.Error("Failed to filter by difficulty", e)
        }
    }

    override suspend fun getDialogsByLanguage(languageCode: String): DataResult<List<Dialog>> {
        return try {
            when (val allDialogsResult = getAllDialogs()) {
                is DataResult.Success -> {
                    val filtered = allDialogsResult.data.filter { dialog ->
                        dialog.meta.targetLanguage.code.equals(languageCode, ignoreCase = true)
                    }
                    DataResult.Success(filtered)
                }
                is DataResult.Error -> allDialogsResult
                is DataResult.Loading -> DataResult.Loading
            }
        } catch (e: Exception) {
            DataResult.Error("Failed to filter by language", e)
        }
    }
}