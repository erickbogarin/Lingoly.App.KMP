package com.elab.lingoly.domain.model

data class Dialog(
    val id: String,
    val title: Translation,
    val categoryId: String,
    val subcategoryId: String,
    val tags: List<String>,
    val phrases: List<Phrase>,
    val meta: DialogMeta
) {
    fun getTitle(language: Language): String = title.getOrDefault(language)

    /**
     * Checks if dialog matches any of the given tags.
     * Case-insensitive comparison for flexibility.
     */
    fun hasTag(tag: String): Boolean = tags.any { it.equals(tag, ignoreCase = true) }

    /**
     * Checks if dialog matches all given tags.
     */
    fun hasAllTags(tags: List<String>): Boolean = tags.all { hasTag(it) }

    /**
     * Gets phrases by specific role.
     * Useful for role-play practice.
     * Case-insensitive comparison.
     */
    fun getPhrasesByRole(role: String): List<Phrase> =
        phrases.filter { it.role.equals(role, ignoreCase = true) }

    /**
     * Gets all unique roles in the dialog.
     * Useful for understanding dialog participants.
     */
    fun getRoles(): Set<String> = phrases.map { it.role }.toSet()

    /**
     * Gets count of phrases per role.
     * Useful for balancing dialog practice.
     */
    fun getRoleDistribution(): Map<String, Int> =
        phrases.groupingBy { it.role }.eachCount()
}
