package com.elab.lingoly.domain.usecase

import com.elab.lingoly.domain.model.Category
import com.elab.lingoly.domain.repository.DialogRepository
import com.elab.lingoly.utils.DataResult

class GetCategoriesUseCase(
    private val dialogRepository: DialogRepository
) {
    suspend operator fun invoke(): DataResult<List<Category>> {
        return dialogRepository.getCategories()
    }
}