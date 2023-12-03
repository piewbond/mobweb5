package hu.bme.aut.android.jot.feature.excercise_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import hu.bme.aut.android.jot.ExcerciseApplication
import hu.bme.aut.android.jot.domain.usecases.ExcerciseUseCases
import hu.bme.aut.android.jot.ui.model.ExcerciseUi
import hu.bme.aut.android.jot.ui.model.asExcerciseUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ExcerciseListState {
    object Loading : ExcerciseListState()
    data class Error(val error: Throwable) : ExcerciseListState()
    data class Result(val excerciseList : List<ExcerciseUi>) : ExcerciseListState()
}

class ExcerciseListViewModel(
    private val excerciseOperations: ExcerciseUseCases
) : ViewModel() {
    private val _state = MutableStateFlow<ExcerciseListState>(ExcerciseListState.Loading)
    val state = _state.asStateFlow()

    fun loadExcercises() {
        viewModelScope.launch {
            try {
                _state.value = ExcerciseListState.Loading
                val excercises = excerciseOperations.loadExcercises().getOrThrow().map { it.asExcerciseUi() }
                _state.value = ExcerciseListState.Result(
                    excerciseList = excercises
                )
            } catch (e: Exception) {
                _state.value = ExcerciseListState.Error(e)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val excerciseOperations = ExcerciseUseCases(ExcerciseApplication.repository)
                ExcerciseListViewModel(
                    excerciseOperations
                )
            }
        }
    }
}