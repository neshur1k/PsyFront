package com.example.angatkinmirea.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Int,
    val title: String,
    val content: String,
    val category: String,
    val authorId: Int,
    val authorNickname: String,
    val createdAt: String
)