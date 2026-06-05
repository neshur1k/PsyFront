package com.example.angatkinmirea.presentation.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FeedScreen(
    viewModel: FeedViewModel
) {

    val articles by
    viewModel.articles.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        items(articles) { article ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(article.title)

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        "Автор: ${article.authorNickname}"
                    )

                    Text(
                        "Категория: ${article.category}"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(article.content)
                }
            }
        }
    }
}