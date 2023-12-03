package hu.bme.aut.android.jot.data.repository

import hu.bme.aut.android.jot.data.dao.ExcerciseDao
import hu.bme.aut.android.jot.data.entities.ExcerciseEntity
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val dao: ExcerciseDao) : TodoRepository {

    override fun getAllTodos(): Flow<List<ExcerciseEntity>> = dao.getAllExcercises()

    override fun getTodoById(id: Int): Flow<ExcerciseEntity> = dao.getExcerciseById(id)

    override suspend fun insertTodo(todo: ExcerciseEntity) { dao.insertExcercise(todo) }

    override suspend fun updateTodo(todo: ExcerciseEntity) { dao.updateExcercise(todo) }

    override suspend fun deleteTodo(id: Int) { dao.deleteExcercise(id) }
}