package hu.bme.aut.android.jot.domain.usecases

import hu.bme.aut.android.jot.data.entities.asTodoEntity
import hu.bme.aut.android.jot.data.repository.TodoRepository
import hu.bme.aut.android.jot.domain.model.Todo
class UpdateTodoUseCase(private val repository: TodoRepository) {

    suspend operator fun invoke(todo: Todo) {
        repository.updateTodo(todo.asTodoEntity())
    }
}

