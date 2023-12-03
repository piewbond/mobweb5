package hu.bme.aut.android.jot.domain.usecases

import hu.bme.aut.android.jot.data.entities.asExcerciseEntity
import hu.bme.aut.android.jot.data.repository.TodoRepository
import hu.bme.aut.android.jot.domain.model.Excercise

class SaveTodoUseCase(private val repository: TodoRepository) {

    suspend operator fun invoke(excercise: Excercise) {
        repository.insertTodo(excercise.asExcerciseEntity())
    }

}