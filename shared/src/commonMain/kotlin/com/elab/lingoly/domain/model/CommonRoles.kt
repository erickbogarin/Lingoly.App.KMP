package com.elab.lingoly.domain.model

object CommonRoles {
    // Hospitality & Service
    const val CUSTOMER = "CUSTOMER"
    const val WAITER = "WAITER"
    const val RECEPTIONIST = "RECEPTIONIST"
    const val GUEST = "GUEST"
    const val HOST = "HOST"
    const val CLERK = "CLERK"

    // Healthcare
    const val DOCTOR = "DOCTOR"
    const val PATIENT = "PATIENT"
    const val NURSE = "NURSE"

    // Education
    const val TEACHER = "TEACHER"
    const val STUDENT = "STUDENT"
    const val PROFESSOR = "PROFESSOR"

    // Business & Professional
    const val COLLEAGUE = "COLLEAGUE"
    const val MANAGER = "MANAGER"
    const val EMPLOYEE = "EMPLOYEE"
    const val CLIENT = "CLIENT"

    // Transportation & Travel
    const val DRIVER = "DRIVER"
    const val PASSENGER = "PASSENGER"
    const val PILOT = "PILOT"
    const val ATTENDANT = "ATTENDANT"

    // Shopping & Retail
    const val SHOPPER = "SHOPPER"
    const val CASHIER = "CASHIER"
    const val SALESPERSON = "SALESPERSON"

    // Generic
    const val GENERIC = "GENERIC"
    const val SPEAKER_A = "SPEAKER_A"
    const val SPEAKER_B = "SPEAKER_B"

    /**
     * Validates if a role string follows naming conventions.
     *
     * Rules:
     * - UPPERCASE with underscores only
     * - 2-50 characters
     * - Letters, numbers, underscores
     */
    fun isValidFormat(role: String): Boolean {
        return role.length in 2..50 &&
                role.matches(Regex("[A-Z0-9_]+"))
    }

    /**
     * Normalizes a role string to standard format.
     *
     * Example: "tour guide" → "TOUR_GUIDE"
     */
    fun normalize(role: String): String {
        return role.trim()
            .uppercase()
            .replace(Regex("\\s+"), "_")
            .replace(Regex("[^A-Z0-9_]"), "")
    }

    /**
     * Gets a display-friendly name for a role.
     *
     * Example: "TOUR_GUIDE" → "Tour Guide"
     */
    fun toDisplayName(role: String): String {
        return role.split('_')
            .joinToString(" ") { word ->
                word.lowercase().replaceFirstChar { it.uppercase() }
            }
    }
}