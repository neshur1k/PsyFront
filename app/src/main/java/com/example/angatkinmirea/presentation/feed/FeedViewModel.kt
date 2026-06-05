package com.example.angatkinmirea.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.angatkinmirea.data.remote.ApiService
import com.example.angatkinmirea.data.repository.ArticleRepositoryImpl
import com.example.angatkinmirea.domain.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {

    private val repository =
        ArticleRepositoryImpl(
            ApiService()
        )

    private val _articles =
        MutableStateFlow<List<Article>>(
            emptyList()
        )

    val articles: StateFlow<List<Article>>
        get() = _articles

    init {
        loadArticles()
    }

    fun loadArticles() {

        viewModelScope.launch {

            try {

                _articles.value =
                    repository.getArticles()

            } catch (e: Exception) {
                println("ERROR: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}