package hu.bme.aut.android.jot.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import hu.bme.aut.android.jot.data.entities.ExcerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExcerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExcercise(excercise: ExcerciseEntity)

    @Query("SELECT * FROM excercise_table")
    fun getAllExcercises(): Flow<List<ExcerciseEntity>>

    @Query("SELECT * FROM excercise_table WHERE id = :id")
    fun getExcerciseById(id: Int): Flow<ExcerciseEntity>

    @Update
    suspend fun updateExcercise(excercise: ExcerciseEntity)

    @Query("DELETE FROM excercise_table WHERE id = :id")
    suspend fun deleteExcercise(id: Int)
}