package com.elab.lingoly.domain.usecase

import com.elab.lingoly.domain.model.Dialog
import com.elab.lingoly.domain.repository.DialogRepository
import com.elab.lingoly.utils.DataResult

class GetDialogUseCase(
    private val dialogRepository: DialogRepository
) {
    suspend operator fun invoke(dialogId: String): DataResult<Dialog> {
        return dialogRepository.getDialog(dialogId)
    }
}