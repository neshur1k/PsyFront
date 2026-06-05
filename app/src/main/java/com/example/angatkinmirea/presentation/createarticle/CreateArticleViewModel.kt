package com.example.angatkinmirea.presentation.createarticle

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.angatkinmirea.data.datastore.TokenStorage
import com.example.angatkinmirea.data.remote.ApiService
import com.example.angatkinmirea.data.repository.ArticleRepositoryImpl
import kotlinx.coroutines.launch

class CreateArticleViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        ArticleRepositoryImpl(
            ApiService()
        )

    private val tokenStorage =
        TokenStorage(application)

    fun createArticle(
        title: String,
        content: String,
        category: String,
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            try {
                println("CREATE ARTICLE CLICK")
                val token =
                    tokenStorage.getToken()
                println("TOKEN = $token")
                if (token == null) {
                    return@launch
                }

                println("SENDING ARTICLE")
                repository.createArticle(
                    token = token,
                    title = title,
                    content = content,
                    category = category
                )
                println("ARTICLE CREATED")

                onSuccess()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}