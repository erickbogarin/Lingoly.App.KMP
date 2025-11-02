package com.elab.lingoly.domain.usecase

import com.elab.lingoly.data.local.LocalUserRepository
import com.elab.lingoly.data.local.PreferencesDataSource
import com.elab.lingoly.utils.DataResult
import com.russhwolf.settings.MapSettings
import kotlin.test.*
import kotlinx.coroutines.test.runTest

class CompleteDialogUseCaseTest {

    private lateinit var repository: LocalUserRepository
    private lateinit var useCase: CompleteDialogUseCase

    @BeforeTest
    fun setup() {
        val settings = MapSettings()
        val preferencesDataSource = PreferencesDataSource(settings)
        repository = LocalUserRepository(preferencesDataSource)
        useCase = CompleteDialogUseCase(repository)
    }

    @Test
    fun `invoke should mark dialog as completed and add XP`() = runTest {
        val result = useCase("d1", 10)

        assertTrue(result is DataResult.Success)

        val userData = (repository.getUserData() as DataResult.Success).data
        assertTrue(userData.completedDialogs.contains("d1"))
        assertEquals(10, userData.xp)
    }

    @Test
    fun `invoke should not duplicate completion`() = runTest {
        useCase("d1", 10)
        useCase("d1", 10) // Complete same dialog twice

        val userData = (repository.getUserData() as DataResult.Success).data
        assertEquals(1, userData.completedDialogs.filter { it == "d1" }.size)
        assertEquals(10, userData.xp) // XP should not be duplicated
    }

    @Test
    fun `invoke should handle multiple dialog completions`() = runTest {
        useCase("d1", 10)
        useCase("d2", 15)
        useCase("d3", 20)

        val userData = (repository.getUserData() as DataResult.Success).data
        assertEquals(3, userData.completedDialogs.size)
        assertEquals(45, userData.xp)
    }
}

