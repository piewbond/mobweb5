package hu.bme.aut.android.jot.data.converters

import androidx.room.TypeConverter
import hu.bme.aut.android.jot.domain.model.Priority
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

object LocalDateConverter {

    @TypeConverter
    fun LocalDate.asString(): String = this.toString()

    @TypeConverter
    fun String.asLocalDateTime(): LocalDate = this.toLocalDate()
}

object TodoPriorityConverter {

    @TypeConverter
    fun Priority.asString(): String = this.name

    @TypeConverter
    fun String.asPriority(): Priority {
        return when(this) {
            Priority.LOW.name -> Priority.LOW
            Priority.MEDIUM.name -> Priority.MEDIUM
            Priority.HIGH.name -> Priority.HIGH
            else -> Priority.LOW
        }
    }
}