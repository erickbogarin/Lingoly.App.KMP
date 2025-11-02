package com.elab.lingoly.testutils.data

import com.elab.lingoly.data.model.dialog.DialogMetaDto

class DialogMetaDtoBuilder {
    var xpReward: Int = 0
    var estimatedTime: String = ""
    var difficulty: String = "easy"
    var targetLanguage: String = "en"

    fun build() = DialogMetaDto(xpReward, estimatedTime, difficulty, targetLanguage)
}