package com.example.angatkinmirea.presentation.articledetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.angatkinmirea.data.remote.ApiService
import com.example.angatkinmirea.data.repository.ArticleRepositoryImpl

class ArticleDetailsViewModelFactory(
    private val api: ApiService
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArticleDetailsViewModel(
            ArticleRepositoryImpl(api)
        ) as T
    }
}