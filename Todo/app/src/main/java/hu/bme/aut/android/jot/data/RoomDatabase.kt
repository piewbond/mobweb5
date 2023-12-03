package hu.bme.aut.android.jot.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.bme.aut.android.jot.data.converters.LocalDateConverter
import hu.bme.aut.android.jot.data.converters.ExcercisePriorityConverter
import hu.bme.aut.android.jot.data.dao.ExcerciseDao
import hu.bme.aut.android.jot.data.entities.TodoEntity

//T93DFS
@Database(entities = [TodoEntity::class], version = 1)
@TypeConverters(ExcercisePriorityConverter::class, LocalDateConverter::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract val dao: ExcerciseDao
}