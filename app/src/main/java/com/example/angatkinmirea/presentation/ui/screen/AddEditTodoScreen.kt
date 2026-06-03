package com.example.angatkinmirea.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.angatkinmirea.domain.model.TodoItem
import com.example.angatkinmirea.presentation.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTodoScreen(
    todoId: Int?,
    viewModel: TodoViewModel,
    onBack: () -> Unit
) {
    val todos by viewModel.todosState.collectAsState()

    val existingTodo = remember(todos, todoId) {
        todos.firstOrNull { it.id == todoId }
    }

    var title by remember {
        mutableStateOf(existingTodo?.title ?: "")
    }

    var description by remember {
        mutableStateOf(existingTodo?.description ?: "")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (todoId == null)
                            "Новая задача"
                        else
                            "Редактирование"
                    )
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Название") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Описание") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    if (todoId == null) {

                        val nextId =
                            (todos.maxOfOrNull { it.id } ?: 0) + 1

                        viewModel.addTodo(
                            TodoItem(
                                id = nextId,
                                title = title,
                                description = description,
                                isCompleted = false
                            )
                        )

                    } else {

                        viewModel.updateTodo(
                            TodoItem(
                                id = todoId,
                                title = title,
                                description = description,
                                isCompleted = existingTodo?.isCompleted ?: false
                            )
                        )
                    }

                    onBack()
                }
            ) {
                Text("Сохранить")
            }
        }
    }
}