package com.elab.lingoly.domain.usecase

import com.elab.lingoly.domain.model.Subcategory
import com.elab.lingoly.domain.repository.DialogRepository
import com.elab.lingoly.utils.DataResult

class GetSubcategoriesUseCase(
    private val dialogRepository: DialogRepository
) {
    suspend operator fun invoke(categoryId: String): DataResult<List<Subcategory>> {
        return dialogRepository.getSubcategories(categoryId)
    }
}