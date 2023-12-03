package hu.bme.aut.android.jot.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import hu.bme.aut.android.jot.data.entities.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExcerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExcercise(excercise: TodoEntity)

    @Query("SELECT * FROM excercise_table")
    fun getAllExcercises(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM excercise_table WHERE id = :id")
    fun getExcerciseById(id: Int): Flow<TodoEntity>

    @Update
    suspend fun updateExcercise(excercise: TodoEntity)

    @Query("DELETE FROM excercise_table WHERE id = :id")
    suspend fun deleteExcercise(id: Int)
}