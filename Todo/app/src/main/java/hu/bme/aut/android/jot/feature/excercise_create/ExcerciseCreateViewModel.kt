package hu.bme.aut.android.jot.feature.excercise_create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.jot.ExcerciseApplication
import hu.bme.aut.android.jot.domain.usecases.ExcerciseUseCases
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

data class ExcerciseCreateState(
    val excerciseUi: ExcerciseUi = ExcerciseUi()
)

sealed class ExcerciseCreateUiEvent{
    object Success : ExcerciseCreateUiEvent()
    data class Failure(val error: UiText) : ExcerciseCreateUiEvent()
}

sealed class ExcerciseCreateEvent {
    data class ChangeTitle(val text: String): ExcerciseCreateEvent()
    data class ChangeDescription(val text: String): ExcerciseCreateEvent()
    data class SelectPriority(val priority: PriorityUi): ExcerciseCreateEvent()
    data class SelectDate(val date: LocalDate): ExcerciseCreateEvent()
    object SaveExcercise: ExcerciseCreateEvent()
}

class ExcerciseCreateViewModel(
    private val excerciseOperations: ExcerciseUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(ExcerciseCreateState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<ExcerciseCreateUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ExcerciseCreateEvent) {
        when(event) {
            is ExcerciseCreateEvent.ChangeTitle -> {
                val newValue = event.text
                _state.update { it.copy(
                    excerciseUi = it.excerciseUi.copy(title = newValue)
                ) }
            }
            is ExcerciseCreateEvent.ChangeDescription -> {
                val newValue = event.text
                _state.update { it.copy(
                    excerciseUi = it.excerciseUi.copy(description = newValue)
                ) }
            }
            is ExcerciseCreateEvent.SelectPriority -> {
                val newValue = event.priority
                _state.update { it.copy(
                    excerciseUi = it.excerciseUi.copy(priority = newValue)
                ) }
            }
            is ExcerciseCreateEvent.SelectDate -> {
                val newValue = event.date
                _state.update { it.copy(
                    excerciseUi = it.excerciseUi.copy(dueDate = newValue.toString())
                ) }
            }
            ExcerciseCreateEvent.SaveExcercise -> {
                onSave()
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            try {
                excerciseOperations.saveExcercise(state.value.excerciseUi.asExcercise())
                _uiEvent.send(ExcerciseCreateUiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(ExcerciseCreateUiEvent.Failure(e.toUiText()))
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val excerciseOperations = ExcerciseUseCases(ExcerciseApplication.repository)
                ExcerciseCreateViewModel(
                    excerciseOperations = excerciseOperations
                )
            }
        }
    }

}