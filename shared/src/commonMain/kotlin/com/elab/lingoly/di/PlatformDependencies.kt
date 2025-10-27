package com.elab.lingoly.di

import com.russhwolf.settings.Settings

interface PlatformDependencies {
    /**
     * Persistent key-value storage.
     */
    val settings: Settings

    /**
     * Reads asset file content.
     */
    fun readJsonAsset(fileName: String): String
}