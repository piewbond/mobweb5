package hu.bme.aut.android.jot.feature.excercise_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.jot.ExcerciseApplication
import hu.bme.aut.android.jot.domain.model.Excercise
import hu.bme.aut.android.jot.domain.usecases.ExcerciseUseCases
import hu.bme.aut.android.jot.feature.excercise_create.ExcerciseCreateUiEvent
import hu.bme.aut.android.jot.ui.model.ExcerciseUi
import hu.bme.aut.android.jot.ui.model.asExcerciseUi
import hu.bme.aut.android.jot.ui.model.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

sealed class ExcerciseDetailState {
    object Loading : ExcerciseDetailState()
    data class Error(val error: Throwable) : ExcerciseDetailState()
    data class Result(val excerciseUi: ExcerciseUi) : ExcerciseDetailState()
}

class ExcerciseDetailViewModel(
    private val excerciseOperations: ExcerciseUseCases,
    private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _state = MutableStateFlow<ExcerciseDetailState>(ExcerciseDetailState.Loading)
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<ExcerciseCreateUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadExcercises()
    }

    private fun loadExcercises() {
        val id = checkNotNull<Int>(savedStateHandle["id"])
        viewModelScope.launch {
            try {
                _state.value = ExcerciseDetailState.Loading
                val excercise = excerciseOperations.loadExcercise(id)
                _state.value = ExcerciseDetailState.Result(
                    excerciseUi = excercise.getOrThrow().asExcerciseUi()
                )
            } catch (e: Exception) {
                _state.value = ExcerciseDetailState.Error(e)
            }
        }
    }
    fun onDelete() {
        val id = checkNotNull<Int>(savedStateHandle["id"])
        viewModelScope.launch {
            try {
                excerciseOperations.deleteExcercise(id)
                _uiEvent.send(ExcerciseCreateUiEvent.Success)
            } catch (e: Exception) {
                _uiEvent.send(ExcerciseCreateUiEvent.Failure(e.toUiText()))
            }
        }
    }
    fun onEdit(excercise: Excercise) {
        val id = checkNotNull<Int>(savedStateHandle["id"])
        viewModelScope.launch {
            try {
                excerciseOperations.updateExcercise(excercise)
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
                val savedStateHandle = createSavedStateHandle()
                ExcerciseDetailViewModel(
                    excerciseOperations,
                    savedStateHandle
                )
            }
        }
    }
}