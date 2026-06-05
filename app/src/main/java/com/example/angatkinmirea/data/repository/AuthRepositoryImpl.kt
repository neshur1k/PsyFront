package com.example.angatkinmirea.data.repository

import com.example.angatkinmirea.data.remote.ApiService
import com.example.angatkinmirea.domain.model.LoginResponse
import com.example.angatkinmirea.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val api: ApiService
) : AuthRepository {

    override suspend fun login(
        login: String,
        password: String
    ): LoginResponse {

        return api.login(login, password)
    }
}