package com.example.angatkinmirea.presentation.articledetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.angatkinmirea.data.repository.ArticleRepositoryImpl
import com.example.angatkinmirea.domain.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticleDetailsViewModel(
    private val repository: ArticleRepositoryImpl
) : ViewModel() {

    private val _article = MutableStateFlow<Article?>(null)
    val article: StateFlow<Article?> = _article

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun loadArticle(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                _article.value = repository.getArticleById(id)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _loading.value = false
            }
        }
    }
}