package com.elab.lingoly.domain.repository

import com.elab.lingoly.domain.model.Category
import com.elab.lingoly.domain.model.Dialog
import com.elab.lingoly.domain.model.Subcategory
import com.elab.lingoly.utils.DataResult

interface DialogRepository {
    suspend fun getCategories(): DataResult<List<Category>>
    suspend fun getSubcategories(categoryId: String): DataResult<List<Subcategory>>
    suspend fun getDialogsBySubcategory(categoryId: String, subcategoryId: String): DataResult<List<Dialog>>
    suspend fun getDialog(dialogId: String): DataResult<Dialog>
    suspend fun getAllDialogs(): DataResult<List<Dialog>>
    suspend fun getDialogsByDifficulty(difficulty: String): DataResult<List<Dialog>>
    suspend fun getDialogsByLanguage(languageCode: String): DataResult<List<Dialog>>
}