package com.example.angatkinmirea.data.repository

import android.content.Context
import com.example.angatkinmirea.data.local.AppDatabase
import com.example.angatkinmirea.data.local.TodoEntity
import com.example.angatkinmirea.data.local.TodoJsonDataSource
import com.example.angatkinmirea.domain.model.TodoItem
import com.example.angatkinmirea.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl private constructor(context: Context) : TodoRepository {

    private val dao = AppDatabase.getDatabase(context).todoDao()
    private val jsonDataSource = TodoJsonDataSource(context)

    private suspend fun ensureDatabaseFilled() {
        if (dao.count() == 0) {
            val todos = jsonDataSource.getTodos().map {
                TodoEntity(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    isCompleted = it.isCompleted
                )
            }
            dao.insertAll(todos)
        }
    }

    suspend fun init() {
        ensureDatabaseFilled()
    }

    override fun getTodos(): Flow<List<TodoItem>> {
        return dao.getTodos().map { list ->
            list.map {
                TodoItem(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    isCompleted = it.isCompleted
                )
            }
        }
    }

    override suspend fun toggleTodo(id: Int) {
        val todo = dao.getById(id) ?: return
        dao.update(todo.copy(isCompleted = !todo.isCompleted))
    }

    override suspend fun addTodo(todo: TodoItem) {
        dao.insert(
            TodoEntity(
                id = todo.id,
                title = todo.title,
                description = todo.description,
                isCompleted = todo.isCompleted
            )
        )
    }

    override suspend fun deleteTodo(todo: TodoItem) {
        dao.delete(
            TodoEntity(
                id = todo.id,
                title = todo.title,
                description = todo.description,
                isCompleted = todo.isCompleted
            )
        )
    }

    override suspend fun updateTodo(todo: TodoItem) {
        dao.update(
            TodoEntity(
                id = todo.id,
                title = todo.title,
                description = todo.description,
                isCompleted = todo.isCompleted
            )
        )
    }

    companion object {
        @Volatile
        private var instance: TodoRepositoryImpl? = null

        fun getInstance(context: Context): TodoRepositoryImpl =
            instance ?: synchronized(this) {
                instance ?: TodoRepositoryImpl(context).also { instance = it }
            }
    }
}