package hu.bme.aut.android.jot.feature.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.jot.TodoApplication
import hu.bme.aut.android.jot.domain.usecases.TodoUseCases
import hu.bme.aut.android.jot.ui.model.TodoUi
import hu.bme.aut.android.jot.ui.model.asTodoUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class TodoListState {
    object Loading : TodoListState()
    data class Error(val error: Throwable) : TodoListState()
    data class Result(val todoList : List<TodoUi>) : TodoListState()
}

class TodoListViewModel(
    private val todoOperations: TodoUseCases
) : ViewModel() {
    private val _state = MutableStateFlow<TodoListState>(TodoListState.Loading)
    val state = _state.asStateFlow()

    fun loadTodos() {
        viewModelScope.launch {
            try {
                _state.value = TodoListState.Loading
                val todos = todoOperations.loadTodos().getOrThrow().map { it.asTodoUi() }
                _state.value = TodoListState.Result(
                    todoList = todos
                )
            } catch (e: Exception) {
                _state.value = TodoListState.Error(e)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val todoOperations = TodoUseCases(TodoApplication.repository)
                TodoListViewModel(
                    todoOperations
                )
            }
        }
    }
}