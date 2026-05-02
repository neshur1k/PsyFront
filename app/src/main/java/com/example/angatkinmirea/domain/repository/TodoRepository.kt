package com.example.angatkinmirea.domain.repository

import com.example.angatkinmirea.domain.model.TodoItem

interface TodoRepository {
    fun getTodos(): kotlinx.coroutines.flow.Flow<List<TodoItem>>

    suspend fun toggleTodo(id: Int)
    suspend fun addTodo(todo: TodoItem)
    suspend fun deleteTodo(todo: TodoItem)
    suspend fun updateTodo(todo: TodoItem)
}
