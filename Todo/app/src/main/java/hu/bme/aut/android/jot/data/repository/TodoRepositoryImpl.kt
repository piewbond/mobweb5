package hu.bme.aut.android.jot.data.repository

import hu.bme.aut.android.jot.data.dao.ExcerciseDao
import hu.bme.aut.android.jot.data.entities.TodoEntity
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val dao: ExcerciseDao) : TodoRepository {

    override fun getAllTodos(): Flow<List<TodoEntity>> = dao.getAllExcercises()

    override fun getTodoById(id: Int): Flow<TodoEntity> = dao.getExcerciseById(id)

    override suspend fun insertTodo(todo: TodoEntity) { dao.insertExcercise(todo) }

    override suspend fun updateTodo(todo: TodoEntity) { dao.updateExcercise(todo) }

    override suspend fun deleteTodo(id: Int) { dao.deleteExcercise(id) }
}