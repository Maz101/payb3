package com.example.paybe

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform