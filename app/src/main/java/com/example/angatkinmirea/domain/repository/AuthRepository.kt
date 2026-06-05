package com.example.angatkinmirea.domain.repository

interface AuthRepository {

    suspend fun login(
        login: String,
        password: String
    ): String
}