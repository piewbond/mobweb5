package hu.bme.aut.android.jot.feature.todo_create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.jot.TodoApplication
import hu.bme.aut.android.jot.domain.usecases.TodoUseCases
import hu.bme.aut.android.jot.ui.model.PriorityUi
import hu.bme.aut.android.jot.ui.model.ExcerciseUi
import hu.bme.aut.android.jot.ui.model.UiText
import hu.bme.aut.android.jot.ui.model.asExcercise
import hu.bme.aut.android.jot.ui.model.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

data class TodoCreateState(
    val todo: ExcerciseUi = ExcerciseUi()
)

sealed class TodoCreateUiEvent{
    object Success : TodoCreateUiEvent()
    data class Failure(val error: UiText) : TodoCreateUiEvent()
}

sealed class TodoCreateEvent {
    data class ChangeTitle(val text: String): TodoCreateEvent()
    data class ChangeDescription(val text: String): TodoCreateEvent()
    data class SelectPriority(val priority: PriorityUi): TodoCreateEvent()
    data class SelectDate(val date: LocalDate): TodoCreateEvent()
    object SaveTodo: TodoCreateEvent()
}

class TodoCreateViewModel(
    private val todoOperations: TodoUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(TodoCreateState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<TodoCreateUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TodoCreateEvent) {
        when(event) {
            is TodoCreateEvent.ChangeTitle -> {
                val newValue = event.text
                _state.update { it.copy(
                    todo = it.todo.copy(title = newValue)
                ) }
            }
            is TodoCreateEvent.ChangeDescription -> {
                val newValue = event.text
                _state.update { it.copy(
                    todo = it.todo.copy(description = newValue)
                ) }
            }
            is TodoCreateEvent.SelectPriority -> {
                val newValue = event.priority
                _state.update { it.copy(
                    todo = it.todo.copy(priority = newValue)
                ) }
            }
            is TodoCreateEvent.SelectDate -> {
                val newValue = event.date
                _state.update { it.copy(
                    todo = it.todo.copy(dueDate = newValue.toString())
                ) }
            }
            TodoCreateEvent.SaveTodo -> {
                onSave()
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            try {
                todoOperations.saveTodo(state.value.todo.asExcercise())
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
                TodoCreateViewModel(
                    todoOperations = todoOperations
                )
            }
        }
    }

}