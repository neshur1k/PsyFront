package com.example.angatkinmirea.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateArticleRequest(
    val title: String,
    val content: String,
    val category: String
)