package com.elab.lingoly.data.mapper

import com.elab.lingoly.domain.model.Language
import com.elab.lingoly.domain.model.UserData
import com.elab.lingoly.testutils.userDataDto
import kotlin.test.*

class UserDataMapperTest {

    @Test
    fun `toDomain should map UserDataDto correctly`() {
        val dto = userDataDto {
            name("João")
            completed("d1", "d2")
            saved("p1", "p2", "p3")
            xp(150)
            streak(7)
            achievements("first_dialog", "week_streak")
            native("pt")
            target("en")
            completedOnboarding()
        }

        val domain = UserDataMapper.toDomain(dto)

        assertEquals("João", domain.name)
        assertEquals(listOf("d1", "d2"), domain.completedDialogs)
        assertEquals(listOf("p1", "p2", "p3"), domain.savedPhrases)
        assertEquals(150, domain.xp)
        assertEquals(7, domain.streak)
        assertEquals(listOf("first_dialog", "week_streak"), domain.achievements)
        assertEquals(Language.PORTUGUESE, domain.nativeLanguage)
        assertEquals(Language.ENGLISH, domain.targetLanguage)
        assertTrue(domain.hasCompletedOnboarding)
    }

    @Test
    fun `toDomain should handle default values`() {
        val dto = userDataDto {
            emptyDefaults()
        }
        val domain = UserDataMapper.toDomain(dto)

        assertEquals("", domain.name)
        assertTrue(domain.completedDialogs.isEmpty())
        assertTrue(domain.savedPhrases.isEmpty())
        assertEquals(0, domain.xp)
        assertEquals(0, domain.streak)
        assertEquals(Language.PORTUGUESE, domain.nativeLanguage)
        assertEquals(Language.ENGLISH, domain.targetLanguage)
        assertFalse(domain.hasCompletedOnboarding)
    }

    @Test
    fun `toDto should map UserData correctly`() {
        val domain = UserData(
            name = "Maria",
            completedDialogs = listOf("d1"),
            savedPhrases = listOf("p1"),
            xp = 50,
            streak = 3,
            achievements = listOf("beginner"),
            nativeLanguage = Language.ENGLISH,
            targetLanguage = Language.SPANISH,
            hasCompletedOnboarding = true
        )

        val dto = UserDataMapper.toDto(domain)

        assertEquals("Maria", dto.name)
        assertEquals(listOf("d1"), dto.completedDialogs)
        assertEquals(listOf("p1"), dto.savedPhrases)
        assertEquals(50, dto.xp)
        assertEquals("en", dto.nativeLanguage)
        assertEquals("es", dto.targetLanguage)
        assertTrue(dto.hasCompletedOnboarding)
    }

    @Test
    fun `toDomain should fallback to default language if code invalid`() {
        val dto = userDataDto {
            native("invalid_code")
            target("en")
        }

        val domain = UserDataMapper.toDomain(dto)

        assertEquals(Language.PORTUGUESE, domain.nativeLanguage)
        assertEquals(Language.ENGLISH, domain.targetLanguage)
    }

    @Test
    fun `bidirectional mapping should preserve data`() {
        val originalDomain = UserData(
            name = "Test User",
            completedDialogs = listOf("d1", "d2", "d3"),
            savedPhrases = listOf("p1"),
            xp = 100,
            streak = 5,
            achievements = listOf("a1", "a2"),
            nativeLanguage = Language.PORTUGUESE,
            targetLanguage = Language.ENGLISH,
            hasCompletedOnboarding = true
        )

        val dto = UserDataMapper.toDto(originalDomain)
        val mappedDomain = UserDataMapper.toDomain(dto)

        assertEquals(originalDomain.name, mappedDomain.name)
        assertEquals(originalDomain.completedDialogs, mappedDomain.completedDialogs)
        assertEquals(originalDomain.savedPhrases, mappedDomain.savedPhrases)
        assertEquals(originalDomain.xp, mappedDomain.xp)
        assertEquals(originalDomain.streak, mappedDomain.streak)
        assertEquals(originalDomain.nativeLanguage, mappedDomain.nativeLanguage)
        assertEquals(originalDomain.targetLanguage, mappedDomain.targetLanguage)
        assertEquals(originalDomain.hasCompletedOnboarding, mappedDomain.hasCompletedOnboarding)
    }
}
