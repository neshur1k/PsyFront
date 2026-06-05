package com.example.angatkinmirea.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.angatkinmirea.data.datastore.TokenStorage
import com.example.angatkinmirea.data.remote.ApiService
import com.example.angatkinmirea.data.repository.AuthRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        AuthRepositoryImpl(
            ApiService()
        )

    private val tokenStorage =
        TokenStorage(
            getApplication()
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
                tokenStorage.saveToken(token)
                _token.value = token

            } catch (e: Exception) {

                _error.value =
                    e.message
            }
        }
    }

    fun logout(onDone: () -> Unit) {
        viewModelScope.launch {
            tokenStorage.clearToken()
            _token.value = null
            onDone()
        }
    }
}