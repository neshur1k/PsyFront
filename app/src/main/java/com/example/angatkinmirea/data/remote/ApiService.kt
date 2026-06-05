package com.example.angatkinmirea.data.remote

import com.example.angatkinmirea.domain.model.Article
import com.example.angatkinmirea.domain.model.LoginRequest
import com.example.angatkinmirea.domain.model.LoginResponse
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.client.request.get
import com.example.angatkinmirea.data.model.CreateArticleRequest
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders


class ApiService {

    private val client = ApiClient.client

    suspend fun login(
        login: String,
        password: String
    ): LoginResponse {

        return client.post(
            "http://10.0.2.2:8080/login"
        ) {

            contentType(ContentType.Application.Json)

            setBody(
                LoginRequest(
                    login,
                    password
                )
            )
        }.body()
    }

    suspend fun getArticles(): List<Article> {

        return client.get(
            "http://10.0.2.2:8080/articles"
        ).body()
    }

    suspend fun createArticle(
        token: String,
        request: CreateArticleRequest
    ) {
        println("TOKEN: $token")
        println("REQUEST: $request")
        client.post(
            "http://10.0.2.2:8080/articles"
        ) {

            header(
                HttpHeaders.Authorization,
                "Bearer $token"
            )

            contentType(
                ContentType.Application.Json
            )

            setBody(request)
        }
    }
}