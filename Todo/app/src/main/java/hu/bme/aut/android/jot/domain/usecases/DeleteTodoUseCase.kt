package hu.bme.aut.android.jot.domain.usecases

import hu.bme.aut.android.jot.data.repository.ExcerciseRepository

class DeleteTodoUseCase(private val repository: ExcerciseRepository) {

    suspend operator fun invoke(id: Int) {
        repository.deleteExcercise(id)
    }

}