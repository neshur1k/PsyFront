package com.example.angatkinmirea.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.angatkinmirea.presentation.ui.component.TodoItemRow
import com.example.angatkinmirea.presentation.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    viewModel: TodoViewModel,
    onOpenDetail: (Int) -> Unit,
    onAddTodo: () -> Unit
) {
    val todos by viewModel.todosState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isColorEnabled by viewModel.isColorEnabled.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Список") },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Цвет завершённых: ")

                        Switch(
                            checked = isColorEnabled,
                            onCheckedChange = {
                                viewModel.onColorToggle(it)
                            }
                        )
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTodo
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить задачу"
                )
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier.padding(padding)
        ) {

            if (isLoading) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            } else {

                LazyColumn {

                    items(todos) { item ->

                        TodoItemRow(
                            item = item,
                            isColorEnabled = isColorEnabled,
                            onCheckedChange = {
                                viewModel.onToggleTodo(it)
                            },
                            onClick = {
                                onOpenDetail(it)
                            }
                        )

                        Divider()
                    }
                }
            }
        }
    }
}