package com.example.angatkinmirea.presentation.articledetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ArticleDetailsScreen(
    articleId: Int,
    viewModel: ArticleDetailsViewModel
) {

    val article by viewModel.article.collectAsState()
    val loading by viewModel.loading.collectAsState()

    LaunchedEffect(articleId) {
        viewModel.loadArticle(articleId)
    }

    if (loading) {
        CircularProgressIndicator()
        return
    }

    article?.let {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(it.title, style = MaterialTheme.typography.headlineMedium)

            Spacer(Modifier.height(8.dp))

            Text("Автор: ${it.authorNickname}")
            Text("Категория: ${it.category}")

            Spacer(Modifier.height(16.dp))

            Text(it.content)
        }
    }
}