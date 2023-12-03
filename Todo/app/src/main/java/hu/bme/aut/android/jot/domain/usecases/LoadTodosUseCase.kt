package hu.bme.aut.android.jot.domain.usecases

import hu.bme.aut.android.jot.data.entities.asExcercise
import hu.bme.aut.android.jot.data.repository.ExcerciseRepository
import hu.bme.aut.android.jot.domain.model.Excercise
import kotlinx.coroutines.flow.first
import java.io.IOException

class LoadTodosUseCase(private val repository: ExcerciseRepository) {

    suspend operator fun invoke(): Result<List<Excercise>> {
        return try {
            val todos = repository.getAllExcercises().first()
            Result.success(todos.map { it.asExcercise() })
        } catch (e: IOException) {
            Result.failure(e)
        }
    }
}