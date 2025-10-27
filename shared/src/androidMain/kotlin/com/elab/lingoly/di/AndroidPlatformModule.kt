package com.elab.lingoly.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

class AndroidPlatformModule(context: Context) : PlatformDependencies {

    private val appContext = context.applicationContext

    override val settings: Settings by lazy {
        SharedPreferencesSettings(
            appContext.getSharedPreferences("lingoly_prefs", Context.MODE_PRIVATE)
        )
    }

    override fun readJsonAsset(fileName: String): String {
        return appContext.assets.open(fileName)
            .bufferedReader()
            .use { it.readText() }
    }
}