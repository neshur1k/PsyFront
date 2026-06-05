package com.example.angatkinmirea.data.repository

import com.example.angatkinmirea.data.remote.ApiService
import com.example.angatkinmirea.domain.model.Article
import com.example.angatkinmirea.domain.repository.ArticleRepository

class ArticleRepositoryImpl(
    private val api: ApiService
) : ArticleRepository {

    override suspend fun getArticles(): List<Article> {

        return api.getArticles()
    }
}