package com.elab.lingoly.di

import com.elab.lingoly.data.local.DialogJsonDataSource
import com.elab.lingoly.data.local.LocalDialogRepository
import com.elab.lingoly.data.local.LocalUserRepository
import com.elab.lingoly.data.local.PreferencesDataSource
import com.elab.lingoly.domain.repository.DialogRepository
import com.elab.lingoly.domain.repository.UserRepository
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal

object ServiceLocator {
    private var platformDeps: PlatformDependencies? = null

    fun init(platform: PlatformDependencies) {
        if (platformDeps == null) {
            platformDeps = platform
        }
    }

    private fun requirePlatform(): PlatformDependencies =
        platformDeps ?: throw IllegalStateException("ServiceLocator not initialized")

    // Data Sources
    private val dialogJsonDataSource by lazy {
        DialogJsonDataSource(requirePlatform().readJsonAsset("lingoly_data.json"))
    }

    private val preferencesDataSource by lazy {
        PreferencesDataSource(requirePlatform().settings)
    }

    // Repositories
    val dialogRepository: DialogRepository by lazy {
        LocalDialogRepository(dialogJsonDataSource)
    }

    val userRepository: UserRepository by lazy {
        LocalUserRepository(preferencesDataSource)
    }
}
