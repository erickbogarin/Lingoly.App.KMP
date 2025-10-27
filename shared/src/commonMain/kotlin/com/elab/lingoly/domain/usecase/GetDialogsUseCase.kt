package com.elab.lingoly.domain.usecase

import com.elab.lingoly.domain.model.Dialog
import com.elab.lingoly.domain.repository.DialogRepository
import com.elab.lingoly.utils.DataResult

class GetDialogsUseCase(
    private val dialogRepository: DialogRepository
) {
    suspend operator fun invoke(categoryId: String, subcategoryId: String): DataResult<List<Dialog>> {
        return dialogRepository.getDialogsBySubcategory(categoryId, subcategoryId)
    }
}