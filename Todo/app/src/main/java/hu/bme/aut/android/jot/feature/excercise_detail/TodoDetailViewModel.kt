package hu.bme.aut.android.jot.feature.excercise_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.jot.TodoApplication
import hu.bme.aut.android.jot.domain.usecases.TodoUseCases
import hu.bme.aut.android.jot.feature.excercise_create.TodoCreateUiEvent
import hu.bme.aut.android.jot.ui.model.ExcerciseUi
import hu.bme.aut.android.jot.ui.model.asExcerciseUi
import hu.bme.aut.android.jot.ui.model.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed class TodoDetailState {
    object Loading : TodoDetailState()
    data class Error(val error: Throwable) : TodoDetailState()
    data class Result(val todo: ExcerciseUi) : TodoDetailState()
}

class TodoDetailViewModel(
    private val todoOperations: TodoUseCases,
    private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _state = MutableStateFlow<TodoDetailState>(TodoDetailState.Loading)
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<TodoCreateUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadTodos()
    }

    private fun loadTodos() {
        val id = checkNotNull<Int>(savedStateHandle["id"])
        viewModelScope.launch {
            try {
                _state.value = TodoDetailState.Loading
                val todo = todoOperations.loadTodo(id)
                _state.value = TodoDetailState.Result(
                    todo = todo.getOrThrow().asExcerciseUi()
                )
            } catch (e: Exception) {
                _state.value = TodoDetailState.Error(e)
            }
        }
    }
    fun onDelete() {
        val id = checkNotNull<Int>(savedStateHandle["id"])
        viewModelScope.launch {
            try {
                todoOperations.deleteTodo(id)
                _uiEvent.send(TodoCreateUiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(TodoCreateUiEvent.Failure(e.toUiText()))
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val todoOperations = TodoUseCases(TodoApplication.repository)
                val savedStateHandle = createSavedStateHandle()
                TodoDetailViewModel(
                    todoOperations,
                    savedStateHandle
                )
            }
        }
    }
}