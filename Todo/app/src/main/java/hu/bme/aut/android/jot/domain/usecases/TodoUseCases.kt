package hu.bme.aut.android.jot.domain.usecases

import hu.bme.aut.android.jot.data.repository.ExcerciseRepository

class TodoUseCases(repository: ExcerciseRepository) {
    val loadTodos = LoadTodosUseCase(repository)
    val loadTodo = LoadTodoUseCase(repository)
    val saveTodo = SaveTodoUseCase(repository)
    val updateTodo = UpdateTodoUseCase(repository)
    val deleteTodo = DeleteTodoUseCase(repository)
}