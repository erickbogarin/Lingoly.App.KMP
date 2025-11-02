package com.elab.lingoly.data.local

import com.elab.lingoly.domain.model.Language
import com.elab.lingoly.utils.DataResult
import com.russhwolf.settings.MapSettings
import kotlin.test.*
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class LocalUserRepositoryTest {

    private lateinit var settings: MapSettings
    private lateinit var preferencesDataSource: PreferencesDataSource
    private lateinit var repository: LocalUserRepository

    @BeforeTest
    fun setup() {
        settings = MapSettings()
        preferencesDataSource = PreferencesDataSource(settings)
        repository = LocalUserRepository(preferencesDataSource)
    }

    @Test
    fun `getUserData should return default user when no data exists`() = runTest {
        val result = repository.getUserData()

        assertTrue(result is DataResult.Success)
        val userData = (result as DataResult.Success).data

        assertEquals("", userData.name)
        assertEquals(0, userData.xp)
        assertTrue(userData.completedDialogs.isEmpty())
        assertTrue(userData.savedPhrases.isEmpty())
    }

    @Test
    fun `saveUserName should persist name`() = runTest {
        val saveResult = repository.saveUserName("João Silva")
        assertTrue(saveResult is DataResult.Success)

        val getUserResult = repository.getUserData()
        assertTrue(getUserResult is DataResult.Success)

        val userData = (getUserResult as DataResult.Success).data
        assertEquals("João Silva", userData.name)
    }

    @Test
    fun `toggleSavedPhrase should add phrase when not saved`() = runTest {
        repository.toggleSavedPhrase("p1")

        val result = repository.getUserData()
        val userData = (result as DataResult.Success).data

        assertTrue(userData.savedPhrases.contains("p1"))
    }

    @Test
    fun `toggleSavedPhrase should remove phrase when already saved`() = runTest {
        // Add phrase
        repository.toggleSavedPhrase("p1")

        // Remove phrase
        repository.toggleSavedPhrase("p1")

        val result = repository.getUserData()
        val userData = (result as DataResult.Success).data

        assertFalse(userData.savedPhrases.contains("p1"))
    }

    @Test
    fun `toggleSavedPhrase should handle multiple phrases`() = runTest {
        repository.toggleSavedPhrase("p1")
        repository.toggleSavedPhrase("p2")
        repository.toggleSavedPhrase("p3")

        val result = repository.getUserData()
        val userData = (result as DataResult.Success).data

        assertEquals(3, userData.savedPhrases.size)
        assertTrue(userData.savedPhrases.containsAll(listOf("p1", "p2", "p3")))
    }

    @Test
    fun `markDialogCompleted should add dialog and XP`() = runTest {
        val result = repository.markDialogCompleted("d1", 10)
        assertTrue(result is DataResult.Success)

        val userData = (repository.getUserData() as DataResult.Success).data

        assertTrue(userData.completedDialogs.contains("d1"))
        assertEquals(10, userData.xp)
    }

    @Test
    fun `markDialogCompleted should not duplicate completed dialog`() = runTest {
        repository.markDialogCompleted("d1", 10)
        repository.markDialogCompleted("d1", 10) // Same dialog again

        val userData = (repository.getUserData() as DataResult.Success).data

        // Should only contain d1 once
        assertEquals(1, userData.completedDialogs.filter { it == "d1" }.size)
        // XP should only be added once
        assertEquals(10, userData.xp)
    }

    @Test
    fun `markDialogCompleted should accumulate XP from multiple dialogs`() = runTest {
        repository.markDialogCompleted("d1", 10)
        repository.markDialogCompleted("d2", 15)
        repository.markDialogCompleted("d3", 20)

        val userData = (repository.getUserData() as DataResult.Success).data

        assertEquals(3, userData.completedDialogs.size)
        assertEquals(45, userData.xp) // 10 + 15 + 20
    }

    @Test
    fun `setNativeLanguage should persist language choice`() = runTest {
        val result = repository.setNativeLanguage(Language.SPANISH)
        assertTrue(result is DataResult.Success)

        val nativeLanguage = repository.getNativeLanguage()
        assertEquals(Language.SPANISH, nativeLanguage)
    }

    @Test
    fun `setTargetLanguage should persist language choice`() = runTest {
        val result = repository.setTargetLanguage(Language.PORTUGUESE)
        assertTrue(result is DataResult.Success)

        val targetLanguage = repository.getTargetLanguage()
        assertEquals(Language.PORTUGUESE, targetLanguage)
    }

    @Test
    fun `getNativeLanguage should return default when not set`() = runTest {
        val language = repository.getNativeLanguage()
        assertEquals(Language.PORTUGUESE, language)
    }

    @Test
    fun `getTargetLanguage should return default when not set`() = runTest {
        val language = repository.getTargetLanguage()
        assertEquals(Language.ENGLISH, language)
    }

    @Test
    fun `completeOnboarding should mark onboarding as completed`() = runTest {
        val result = repository.completeOnboarding()
        assertTrue(result is DataResult.Success)

        val hasCompleted = repository.hasCompletedOnboarding()
        assertTrue(hasCompleted)
    }

    @Test
    fun `hasCompletedOnboarding should return false by default`() = runTest {
        val hasCompleted = repository.hasCompletedOnboarding()
        assertFalse(hasCompleted)
    }

    @Test
    fun `observeUserData should emit updates`() = runTest {
        var emittedData: com.elab.lingoly.domain.model.UserData? = null

        val job = launch {
            repository.observeUserData().collect { userData ->
                emittedData = userData
            }
        }

        // Give time for initial emission
        kotlinx.coroutines.delay(100)

        // Update data
        repository.saveUserName("Test User")
        kotlinx.coroutines.delay(100)

        assertNotNull(emittedData)
        assertEquals("Test User", emittedData?.name)

        job.cancel()
    }
}