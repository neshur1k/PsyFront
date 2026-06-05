package com.example.angatkinmirea.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(
    name = "auth"
)

class TokenStorage(
    private val context: Context
) {

    companion object {

        private val TOKEN_KEY =
            stringPreferencesKey("token")

        private val ROLE_KEY =
            stringPreferencesKey("role")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit {
            it[TOKEN_KEY] = token
        }
    }

    suspend fun getToken(): String? {
        return context.dataStore.data.first()[TOKEN_KEY]
    }

    suspend fun clearToken() {
        context.dataStore.edit {
            it.clear()
        }
    }

    suspend fun saveRole(role: String) {
        context.dataStore.edit {
            it[ROLE_KEY] = role
        }
    }

    suspend fun getRole(): String? {
        return context.dataStore.data.first()[ROLE_KEY]
    }
}