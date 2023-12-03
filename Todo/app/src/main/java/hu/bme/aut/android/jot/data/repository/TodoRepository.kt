package hu.bme.aut.android.jot.data.repository

import hu.bme.aut.android.jot.data.entities.ExcerciseEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getAllTodos(): Flow<List<ExcerciseEntity>>

    fun getTodoById(id: Int): Flow<ExcerciseEntity>

    suspend fun insertTodo(todo: ExcerciseEntity)

    suspend fun updateTodo(todo: ExcerciseEntity)

    suspend fun deleteTodo(id: Int)
}