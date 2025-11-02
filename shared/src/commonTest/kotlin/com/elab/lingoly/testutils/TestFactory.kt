package com.elab.lingoly.testutils

import com.elab.lingoly.testutils.data.CategoryDtoBuilder
import com.elab.lingoly.testutils.data.DialogDtoBuilder
import com.elab.lingoly.testutils.data.DialogMetaDtoBuilder
import com.elab.lingoly.testutils.data.PhraseDtoBuilder
import com.elab.lingoly.testutils.data.SubcategoryDtoBuilder
import com.elab.lingoly.testutils.domain.DialogBuilder
import com.elab.lingoly.testutils.data.UserDataDtoBuilder

fun categoryDto(block: CategoryDtoBuilder.() -> Unit) = CategoryDtoBuilder().apply(block).build()

fun dialog(block: DialogBuilder.() -> Unit) = DialogBuilder().apply(block).build()

fun dialogDto(block: DialogDtoBuilder.() -> Unit) = DialogDtoBuilder().apply(block).build()

fun dialogMetaDto(block: DialogMetaDtoBuilder.() -> Unit) = DialogMetaDtoBuilder().apply(block).build()

fun phraseDto(block: PhraseDtoBuilder.() -> Unit) = PhraseDtoBuilder().apply(block).build()
fun userDataDto(block: UserDataDtoBuilder.() -> Unit) = UserDataDtoBuilder().apply(block).build()

fun subcategoryDto(block: SubcategoryDtoBuilder.() -> Unit) = SubcategoryDtoBuilder().apply(block).build()

