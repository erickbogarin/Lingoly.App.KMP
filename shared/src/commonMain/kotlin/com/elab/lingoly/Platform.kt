package com.elab.lingoly

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform