package hu.bme.aut.android.jot.domain.usecases

import hu.bme.aut.android.jot.data.entities.asTodo
import hu.bme.aut.android.jot.data.repository.TodoRepository
import hu.bme.aut.android.jot.domain.model.Excercise
import kotlinx.coroutines.flow.first

import java.io.IOException


class LoadTodoUseCase(private val repository: TodoRepository) {

    suspend operator fun invoke(id: Int): Result<Excercise> {
        return try {
            Result.success(repository.getTodoById(id).first().asTodo())
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

}