package com.example.angatkinmirea.domain.repository

import com.example.angatkinmirea.domain.model.Article


interface ArticleRepository {

    suspend fun getArticles(): List<Article>

    suspend fun createArticle(
        token: String,
        title: String,
        content: String,
        category: String
    )
}