package com.example.angatkinmirea.presentation.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.angatkinmirea.data.datastore.TokenStorage
import kotlinx.coroutines.runBlocking

@Composable
fun FeedScreen(
    viewModel: FeedViewModel,
    onArticleClick: (Int) -> Unit
) {

    val articles by viewModel.articles.collectAsState()

    val context = LocalContext.current
    val tokenStorage = remember { TokenStorage(context) }

    val token = runBlocking {
        tokenStorage.getToken()
    }

    val role = runBlocking {
        tokenStorage.getRole()
    }

    val isAdmin = role == "ADMIN"

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

                    Spacer(Modifier.height(8.dp))

                    if (isAdmin) {
                        Button(
                            onClick = {
                                viewModel.deleteArticle(article.id, token!!)
                            }
                        ) {
                            Text("Удалить")
                        }
                    }
                }
            }
        }
    }
}