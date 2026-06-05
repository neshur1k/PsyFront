package com.example.angatkinmirea.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val login: String,
    val nickname: String,
    val bio: String,
    val role: String
)