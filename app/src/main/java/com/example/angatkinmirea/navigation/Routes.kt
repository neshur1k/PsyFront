package com.example.angatkinmirea.navigation

object Routes {

    const val LOGIN = "login"

    const val REGISTER = "register"

    const val FEED = "feed"

    const val CREATE_ARTICLE = "create_article"

    const val MEDITATION = "meditation"

    const val PROFILE = "profile"

    const val ARTICLE_DETAILS = "article_details/{articleId}"

    fun articleDetails(id: Int): String {
        return "article_details/$id"
    }
}