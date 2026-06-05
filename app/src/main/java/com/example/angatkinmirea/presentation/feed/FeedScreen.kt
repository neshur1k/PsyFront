package com.example.angatkinmirea.presentation.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.angatkinmirea.data.datastore.TokenStorage
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    viewModel: FeedViewModel,
    onArticleClick: (Int) -> Unit
) {

    val articles by viewModel.articles.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val history by viewModel.searchHistory.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val tokenStorage = remember {
        TokenStorage(context)
    }

    val token = runBlocking {
        tokenStorage.getToken()
    }

    val role = runBlocking {
        tokenStorage.getRole()
    }

    val isAdmin = role == "ADMIN"

    var searchFocused by remember {
        mutableStateOf(false)
    }

    var categoryExpanded by remember {
        mutableStateOf(false)
    }

    val selectedCategory by
    viewModel.selectedCategory.collectAsState()

    val categories = mapOf(
        "Тревога" to "ANXIETY",
        "Стресс" to "STRESS",
        "Медитация" to "MEDITATION",
        "Мотивация" to "MOTIVATION",
        "Самооценка" to "SELF_ESTEEM"
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                viewModel.updateSearchQuery(it)
            },
            placeholder = {
                Text("Поиск статей...")
            },
            trailingIcon = {

                if (searchQuery.isNotEmpty()) {

                    IconButton(
                        onClick = {

                            viewModel.updateSearchQuery("")

                            focusManager.clearFocus()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Очистить"
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .onFocusChanged {
                    searchFocused = it.isFocused
                }
        )

        ExposedDropdownMenuBox(
            expanded = categoryExpanded,
            onExpandedChange = {
                categoryExpanded = !categoryExpanded
            }
        ) {

            OutlinedTextField(
                value = categories.entries
                    .find {
                        it.value == selectedCategory
                    }
                    ?.key ?: "Все категории",

                onValueChange = {},

                readOnly = true,

                label = {
                    Text("Категория")
                },

                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = categoryExpanded
                    )
                },

                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            ExposedDropdownMenu(
                expanded = categoryExpanded,
                onDismissRequest = {
                    categoryExpanded = false
                }
            ) {

                DropdownMenuItem(
                    text = {
                        Text("Все категории")
                    },
                    onClick = {
                        viewModel.selectCategory(null)
                        categoryExpanded = false
                    }
                )

                categories.forEach { (name, value) ->

                    DropdownMenuItem(
                        text = {
                            Text(name)
                        },
                        onClick = {
                            viewModel.selectCategory(value)
                            categoryExpanded = false
                        }
                    )
                }
            }
        }

        if (searchFocused && history.isNotEmpty()) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {

                Column(
                    modifier = Modifier.padding(8.dp)
                ) {

                    Text(
                        "История поиска",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    history.forEach { item ->

                        Text(
                            text = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.updateSearchQuery(item)
                                }
                                .padding(8.dp)
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.clearHistory()
                        }
                    ) {
                        Text("Очистить историю")
                    }
                }
            }
        }

        if (loading) {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                CircularProgressIndicator(
                    modifier = Modifier.padding(24.dp)
                )
            }

            return
        }

        if (error != null) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Column {

                    Text(
                        text = error ?: "Ошибка"
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Button(
                        onClick = {
                            viewModel.loadArticles()
                        }
                    ) {
                        Text("Обновить")
                    }
                }
            }

            return
        }

        if (
            articles.isEmpty() &&
            searchQuery.isNotBlank()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Text(
                    "Ничего не найдено"
                )
            }

            return
        }

        LazyColumn {

            items(articles) { article ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    onClick = {

                        viewModel.addToHistory(
                            searchQuery
                        )

                        onArticleClick(
                            article.id
                        )
                    }
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = article.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            "Автор: ${article.authorNickname}"
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        if (isAdmin) {

                            Button(
                                onClick = {
                                    viewModel.deleteArticle(
                                        article.id,
                                        token!!
                                    )
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
}