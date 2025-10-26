package com.elab.lingoly.domain.service

import com.elab.lingoly.domain.model.Dialog
import com.elab.lingoly.domain.repository.DialogRepository
import com.elab.lingoly.utils.DataResult

class DialogQueryService(
    private val dialogRepository: DialogRepository
) {
    /**
     * Searches dialogs by a single tag.
     */
    suspend fun searchByTag(tag: String): DataResult<List<Dialog>> {
        return when (val result = dialogRepository.getAllDialogs()) {
            is DataResult.Success -> {
                val filtered = result.data.filter { it.hasTag(tag) }
                DataResult.Success(filtered)
            }
            is DataResult.Error -> result
            else -> DataResult.Error("Unknown error")
        }
    }

    /**
     * Searches dialogs matching ANY of the provided tags.
     */
    suspend fun searchByTags(tags: List<String>): DataResult<List<Dialog>> {
        if (tags.isEmpty()) {
            return dialogRepository.getAllDialogs()
        }

        return when (val result = dialogRepository.getAllDialogs()) {
            is DataResult.Success -> {
                val filtered = result.data.filter { dialog ->
                    tags.any { tag -> dialog.hasTag(tag) }
                }
                DataResult.Success(filtered)
            }
            is DataResult.Error -> result
            else -> DataResult.Error("Unknown error")
        }
    }

    /**
     * Searches dialogs matching ALL of the provided tags.
     */
    suspend fun searchByAllTags(tags: List<String>): DataResult<List<Dialog>> {
        if (tags.isEmpty()) {
            return dialogRepository.getAllDialogs()
        }

        return when (val result = dialogRepository.getAllDialogs()) {
            is DataResult.Success -> {
                val filtered = result.data.filter { it.hasAllTags(tags) }
                DataResult.Success(filtered)
            }
            is DataResult.Error -> result
            else -> DataResult.Error("Unknown error")
        }
    }

    /**
     * Finds dialogs containing phrases with specified role.
     */
    suspend fun findByRole(role: String): DataResult<List<Dialog>> {
        return when (val result = dialogRepository.getAllDialogs()) {
            is DataResult.Success -> {
                val filtered = result.data.filter { dialog ->
                    dialog.phrases.any { it.role.equals(role, ignoreCase = true) }
                }
                DataResult.Success(filtered)
            }
            is DataResult.Error -> result
            else -> DataResult.Error("Unknown error")
        }
    }

    /**
     * Finds dialogs by difficulty level.
     */
    suspend fun findByDifficulty(difficulty: String): DataResult<List<Dialog>> {
        return when (val result = dialogRepository.getAllDialogs()) {
            is DataResult.Success -> {
                val filtered = result.data.filter {
                    it.meta.difficulty.equals(difficulty, ignoreCase = true)
                }
                DataResult.Success(filtered)
            }
            is DataResult.Error -> result
            else -> DataResult.Error("Unknown error")
        }
    }

    /**
     * Gets all unique roles across all dialogs.
     */
    suspend fun getAllRoles(): DataResult<Set<String>> {
        return when (val result = dialogRepository.getAllDialogs()) {
            is DataResult.Success -> {
                val allRoles = result.data
                    .flatMap { it.phrases }
                    .map { it.role }
                    .toSet()
                DataResult.Success(allRoles)
            }
            is DataResult.Error -> result
            else -> DataResult.Error("Unknown error")
        }
    }

    /**
     * Gets all unique tags across all dialogs.
     */
    suspend fun getAllTags(): DataResult<Set<String>> {
        return when (val result = dialogRepository.getAllDialogs()) {
            is DataResult.Success -> {
                val allTags = result.data
                    .flatMap { it.tags }
                    .toSet()
                DataResult.Success(allTags)
            }
            is DataResult.Error -> result
            else -> DataResult.Error("Unknown error")
        }
    }

    /**
     * Complex search with multiple criteria.
     */
    suspend fun search(
        tags: List<String> = emptyList(),
        role: String? = null,
        difficulty: String? = null,
        minXp: Int? = null,
        maxXp: Int? = null
    ): DataResult<List<Dialog>> {
        return when (val result = dialogRepository.getAllDialogs()) {
            is DataResult.Success -> {
                var filtered = result.data

                // Filter by tags
                if (tags.isNotEmpty()) {
                    filtered = filtered.filter { dialog ->
                        tags.any { tag -> dialog.hasTag(tag) }
                    }
                }

                // Filter by role
                role?.let { r ->
                    filtered = filtered.filter { dialog ->
                        dialog.phrases.any { it.role.equals(r, ignoreCase = true) }
                    }
                }

                // Filter by difficulty
                difficulty?.let { d ->
                    filtered = filtered.filter {
                        it.meta.difficulty.equals(d, ignoreCase = true)
                    }
                }

                // Filter by XP range
                minXp?.let { min ->
                    filtered = filtered.filter { it.meta.xpReward >= min }
                }
                maxXp?.let { max ->
                    filtered = filtered.filter { it.meta.xpReward <= max }
                }

                DataResult.Success(filtered)
            }
            is DataResult.Error -> result
            else -> DataResult.Error("Unknown error")
        }
    }
}