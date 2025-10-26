package com.elab.lingoly.data.local

import com.elab.lingoly.data.mapper.DialogMapper
import com.elab.lingoly.domain.model.Category
import com.elab.lingoly.domain.model.Dialog
import com.elab.lingoly.domain.model.Subcategory
import com.elab.lingoly.domain.repository.DialogRepository
import com.elab.lingoly.utils.DataResult

class LocalDialogRepository(
    private val jsonDataSource: JsonDataSource
) : DialogRepository {

    override suspend fun getCategories(): DataResult<List<Category>> {
        return try {
            val data = jsonDataSource.loadLingolyData()
            val categories = data.categories.map { DialogMapper.toDomain(it) }
            DataResult.Success(categories)
        } catch (e: Exception) {
            DataResult.Error("Failed to load categories", e)
        }
    }

    override suspend fun getSubcategories(categoryId: String): DataResult<List<Subcategory>> {
        return try {
            val data = jsonDataSource.loadLingolyData()
            val category = data.categories.find { it.id == categoryId }

            if (category != null) {
                val subcategories = category.subcategories
                    .map { DialogMapper.toDomain(it, categoryId) }
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
            val data = jsonDataSource.loadLingolyData()
            val category = data.categories.find { it.id == categoryId }
            val subcategory = category?.subcategories?.find { it.id == subcategoryId }

            if (subcategory != null) {
                val dialogs = subcategory.dialogs
                    .map { DialogMapper.toDomain(it, categoryId, subcategoryId) }
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
            val data = jsonDataSource.loadLingolyData()

            for (category in data.categories) {
                for (subcategory in category.subcategories) {
                    val dialogDto = subcategory.dialogs.find { it.id == dialogId }
                    if (dialogDto != null) {
                        val dialog = DialogMapper.toDomain(
                            dialogDto,
                            category.id,
                            subcategory.id
                        )
                        return DataResult.Success(dialog)
                    }
                }
            }
            DataResult.Error("Dialog not found: $dialogId")
        } catch (e: Exception) {
            DataResult.Error("Failed to load dialog", e)
        }
    }

    override suspend fun getAllDialogs(): DataResult<List<Dialog>> {
        return try {
            val data = jsonDataSource.loadLingolyData()
            val allDialogs = mutableListOf<Dialog>()

            for (category in data.categories) {
                for (subcategory in category.subcategories) {
                    val dialogs = subcategory.dialogs
                        .map { DialogMapper.toDomain(it, category.id, subcategory.id) }
                    allDialogs.addAll(dialogs)
                }
            }
            DataResult.Success(allDialogs)
        } catch (e: Exception) {
            DataResult.Error("Failed to load all dialogs", e)
        }
    }
}