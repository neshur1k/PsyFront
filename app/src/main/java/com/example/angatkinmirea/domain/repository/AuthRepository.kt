package com.example.angatkinmirea.domain.repository

import com.example.angatkinmirea.domain.model.LoginResponse

interface AuthRepository {

    suspend fun login(
        login: String,
        password: String
    ): LoginResponse
}