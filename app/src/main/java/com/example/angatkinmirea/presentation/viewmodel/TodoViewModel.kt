package com.example.angatkinmirea.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.angatkinmirea.data.local.SettingsDataStore
import com.example.angatkinmirea.data.repository.TodoRepositoryImpl
import com.example.angatkinmirea.domain.model.TodoItem
import com.example.angatkinmirea.domain.usecase.GetTodosUseCase
import com.example.angatkinmirea.domain.usecase.ToggleTodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(
    private val repository: TodoRepositoryImpl,
    private val getTodosUseCase: GetTodosUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val _todosState = MutableStateFlow<List<TodoItem>>(emptyList())
    val todosState: StateFlow<List<TodoItem>> = _todosState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isColorEnabled = MutableStateFlow(false)
    val isColorEnabled: StateFlow<Boolean> = _isColorEnabled

    init {
        // 🔥 1. Инициализация БД (импорт JSON один раз)
        viewModelScope.launch {
            repository.init()
        }

        // 🔥 2. Подписка на Flow из Room
        viewModelScope.launch {
            getTodosUseCase().collect { todos ->
                _todosState.value = todos
                _isLoading.value = false
            }
        }

        viewModelScope.launch {
            settingsDataStore.isCompletedColorEnabled.collect {
                _isColorEnabled.value = it
            }
        }
    }

    fun onToggleTodo(id: Int) {
        viewModelScope.launch {
            toggleTodoUseCase(id)
        }
    }

    // (понадобится для задания — CRUD)
    fun addTodo(todo: TodoItem) {
        viewModelScope.launch {
            repository.addTodo(todo)
        }
    }

    fun deleteTodo(todo: TodoItem) {
        viewModelScope.launch {
            repository.deleteTodo(todo)
        }
    }

    fun updateTodo(todo: TodoItem) {
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }

    fun onColorToggle(enabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.setCompletedColor(enabled)
        }
    }

    class TodoViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {

            val repository = TodoRepositoryImpl.getInstance(context)
            val getTodosUseCase = GetTodosUseCase(repository)
            val toggleTodoUseCase = ToggleTodoUseCase(repository)
            val settingsDataStore = SettingsDataStore(context)

            return TodoViewModel(
                repository,
                getTodosUseCase,
                toggleTodoUseCase,
                settingsDataStore
            ) as T
        }
    }
}