package hu.bme.aut.android.jot.domain.usecases

import hu.bme.aut.android.jot.data.entities.asExcerciseEntity
import hu.bme.aut.android.jot.data.repository.ExcerciseRepository
import hu.bme.aut.android.jot.domain.model.Excercise
class UpdateTodoUseCase(private val repository: ExcerciseRepository) {

    suspend operator fun invoke(excercise: Excercise) {
        repository.updateExcercise(excercise.asExcerciseEntity())
    }
}

