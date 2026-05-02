package com.example.angatkinmirea.domain.usecase

import com.example.angatkinmirea.domain.model.TodoItem
import com.example.angatkinmirea.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class GetTodosUseCase(private val repository: TodoRepository) {
    operator fun invoke(): Flow<List<TodoItem>> = repository.getTodos()
}
