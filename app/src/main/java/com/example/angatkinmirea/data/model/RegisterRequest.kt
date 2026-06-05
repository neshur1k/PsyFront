package com.example.angatkinmirea.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val login: String,
    val password: String,
    val nickname: String
)