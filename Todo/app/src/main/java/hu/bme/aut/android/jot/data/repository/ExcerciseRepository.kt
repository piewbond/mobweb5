package hu.bme.aut.android.jot.data.repository

import hu.bme.aut.android.jot.data.entities.ExcerciseEntity
import kotlinx.coroutines.flow.Flow

interface ExcerciseRepository {
    fun getAllExcercises(): Flow<List<ExcerciseEntity>>

    fun getExcerciseById(id: Int): Flow<ExcerciseEntity>

    suspend fun insertExcercise(excercise: ExcerciseEntity)

    suspend fun updateExcercise(excercise: ExcerciseEntity)

    suspend fun deleteExcercise(id: Int)
}