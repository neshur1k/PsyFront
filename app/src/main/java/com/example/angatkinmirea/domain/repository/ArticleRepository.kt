package com.example.angatkinmirea.domain.repository

import com.example.angatkinmirea.domain.model.Article


interface ArticleRepository {

    suspend fun getArticles(): List<Article>
}