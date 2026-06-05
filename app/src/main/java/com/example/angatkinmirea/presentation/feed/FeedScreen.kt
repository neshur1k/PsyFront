package com.example.angatkinmirea.presentation.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FeedScreen(
    viewModel: FeedViewModel,
    onArticleClick: (Int) -> Unit
) {

    val articles by viewModel.articles.collectAsState()

    LazyColumn {
        items(articles) { article ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    onClick = {
                        onArticleClick(article.id)
                    }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(4.dp))

                    Text("Автор: ${article.authorNickname}")
                }
            }
        }
    }
}