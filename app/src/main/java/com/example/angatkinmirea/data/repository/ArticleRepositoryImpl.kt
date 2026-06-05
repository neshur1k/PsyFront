package com.example.angatkinmirea.data.repository

import com.example.angatkinmirea.data.model.CreateArticleRequest
import com.example.angatkinmirea.data.remote.ApiService
import com.example.angatkinmirea.domain.model.Article
import com.example.angatkinmirea.domain.repository.ArticleRepository

class ArticleRepositoryImpl(
    private val api: ApiService
) : ArticleRepository {

    override suspend fun getArticles(): List<Article> {

        return api.getArticles()
    }

    override suspend fun createArticle(
        token: String,
        title: String,
        content: String,
        category: String
    ) {

        api.createArticle(
            token = token,
            request = CreateArticleRequest(
                title = title,
                content = content,
                category = category
            )
        )
    }

    suspend fun getArticleById(id: Int) =
        api.getArticleById(id)
}