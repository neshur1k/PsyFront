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
        MutableStateFlow<List<Article>>(emptyList())

    val articles: StateFlow<List<Article>>
        get() = _articles

    private var allArticles: List<Article> =
        emptyList()

    private val _searchQuery =
        MutableStateFlow("")

    val searchQuery: StateFlow<String> =
        _searchQuery

    private val _selectedCategory =
        MutableStateFlow<String?>(null)

    val selectedCategory: StateFlow<String?> =
        _selectedCategory

    private val _searchHistory =
        MutableStateFlow<List<String>>(emptyList())

    val searchHistory: StateFlow<List<String>> =
        _searchHistory

    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error

    private val _loading =
        MutableStateFlow(false)

    val loading: StateFlow<Boolean> =
        _loading

    private var lastQuery = ""

    init {
        loadArticles()
    }

    fun loadArticles() {

        viewModelScope.launch {

            try {

                _loading.value = true
                _error.value = null

                allArticles =
                    repository.getArticles()

                applyFilters()

            } catch (e: Exception) {

                _error.value =
                    e.message ?: "Ошибка загрузки"

            } finally {

                _loading.value = false
            }
        }
    }

    fun updateSearchQuery(
        query: String
    ) {

        _searchQuery.value = query
        lastQuery = query

        applyFilters()
    }

    private fun applyFilters() {

        var result = allArticles

        val query =
            _searchQuery.value

        val category =
            _selectedCategory.value

        if (query.isNotBlank()) {

            result = result.filter {

                it.title.contains(
                    query,
                    ignoreCase = true
                )
            }
        }

        if (category != null) {

            result = result.filter {
                it.category == category
            }
        }

        _articles.value = result
    }

    fun addToHistory(
        query: String
    ) {

        if (query.isBlank()) {
            return
        }

        val updated =
            listOf(query) +
                    _searchHistory.value.filter {
                        it != query
                    }

        _searchHistory.value =
            updated.take(10)
    }

    fun clearHistory() {

        _searchHistory.value =
            emptyList()
    }

    fun retrySearch() {

        loadArticles()
    }

    fun deleteArticle(
        id: Int,
        token: String
    ) {

        viewModelScope.launch {

            try {

                repository.deleteArticle(
                    id,
                    token
                )

                _articles.value =
                    _articles.value.filterNot {
                        it.id == id
                    }

                allArticles =
                    allArticles.filterNot {
                        it.id == id
                    }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun selectCategory(
        category: String?
    ) {

        _selectedCategory.value = category

        applyFilters()
    }
}