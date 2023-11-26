package hu.bme.aut.android.jot.domain.usecases

import hu.bme.aut.android.jot.data.repository.TodoRepository

class DeleteTodoUseCase(private val repository: TodoRepository) {

    suspend operator fun invoke(id: Int) {
        repository.deleteTodo(id)
    }

}