package com.elab.lingoly.domain.usecase

import com.elab.lingoly.data.local.LocalUserRepository
import com.elab.lingoly.data.local.PreferencesDataSource
import com.elab.lingoly.domain.model.Language
import com.elab.lingoly.utils.DataResult
import com.russhwolf.settings.MapSettings
import kotlin.test.*
import kotlinx.coroutines.test.runTest

class SetupLanguagePreferencesUseCaseTest {

    private lateinit var repository: LocalUserRepository
    private lateinit var useCase: SetupLanguagePreferencesUseCase

    @BeforeTest
    fun setup() {
        val settings = MapSettings()
        val preferencesDataSource = PreferencesDataSource(settings)
        repository = LocalUserRepository(preferencesDataSource)
        useCase = SetupLanguagePreferencesUseCase(repository)
    }

    @Test
    fun `invoke should set both languages successfully`() = runTest {
        val result = useCase(Language.PORTUGUESE, Language.ENGLISH)

        assertTrue(result is DataResult.Success)

        val nativeLanguage = repository.getNativeLanguage()
        val targetLanguage = repository.getTargetLanguage()

        assertEquals(Language.PORTUGUESE, nativeLanguage)
        assertEquals(Language.ENGLISH, targetLanguage)
    }

    @Test
    fun `invoke should return error when languages are the same`() = runTest {
        val result = useCase(Language.ENGLISH, Language.ENGLISH)

        assertTrue(result is DataResult.Error)
        assertEquals(
            "Native and target languages must be different",
            (result as DataResult.Error).message
        )
    }

    @Test
    fun `invoke should handle all language combinations`() = runTest {
        val languages = listOf(
            Language.PORTUGUESE to Language.ENGLISH,
            Language.ENGLISH to Language.SPANISH,
            Language.SPANISH to Language.PORTUGUESE
        )

        languages.forEach { (native, target) ->
            val result = useCase(native, target)
            assertTrue(result is DataResult.Success, "Failed for $native -> $target")

            assertEquals(native, repository.getNativeLanguage())
            assertEquals(target, repository.getTargetLanguage())
        }
    }
}

