package hu.bme.aut.android.jot.data.repository

import hu.bme.aut.android.jot.data.dao.ExcerciseDao
import hu.bme.aut.android.jot.data.entities.ExcerciseEntity
import kotlinx.coroutines.flow.Flow

class ExcerciseRepositoryImpl(private val dao: ExcerciseDao) : ExcerciseRepository {

    override fun getAllExcercises(): Flow<List<ExcerciseEntity>> = dao.getAllExcercises()

    override fun getExcerciseById(id: Int): Flow<ExcerciseEntity> = dao.getExcerciseById(id)

    override suspend fun insertExcercise(excercise: ExcerciseEntity) { dao.insertExcercise(excercise) }

    override suspend fun updateExcercise(excercise: ExcerciseEntity) { dao.updateExcercise(excercise) }

    override suspend fun deleteExcercise(id: Int) { dao.deleteExcercise(id) }
}