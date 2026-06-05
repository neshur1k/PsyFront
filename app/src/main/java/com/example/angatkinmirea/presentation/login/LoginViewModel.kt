package com.example.angatkinmirea.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.angatkinmirea.data.remote.ApiService
import com.example.angatkinmirea.data.repository.AuthRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository =
        AuthRepositoryImpl(
            ApiService()
        )

    private val _token =
        MutableStateFlow<String?>(null)

    val token: StateFlow<String?> =
        _token

    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error

    fun login(
        login: String,
        password: String
    ) {

        viewModelScope.launch {

            try {

                val token =
                    repository.login(
                        login,
                        password
                    )

                _token.value = token

            } catch (e: Exception) {

                _error.value =
                    e.message
            }
        }
    }
}