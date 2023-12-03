package hu.bme.aut.android.jot.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.bme.aut.android.jot.domain.model.Priority
import hu.bme.aut.android.jot.domain.model.Excercise
import kotlinx.datetime.LocalDate

@Entity(tableName = "excercise_table")
data class ExcerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val priority: Priority,
    val dueDate: LocalDate,
    val description: String
)

fun ExcerciseEntity.asExcercise(): Excercise = Excercise(
    id = id,
    title = title,
    priority = priority,
    dueDate = dueDate,
    description = description
)

fun Excercise.asExcerciseEntity(): ExcerciseEntity = ExcerciseEntity(
    id = id,
    title = title,
    priority = priority,
    dueDate = dueDate,
    description = description
)