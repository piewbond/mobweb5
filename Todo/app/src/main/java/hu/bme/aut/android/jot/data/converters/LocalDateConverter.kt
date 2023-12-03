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

object ExcercisePriorityConverter {

    @TypeConverter
    fun Priority.asString(): String = this.name

    @TypeConverter
    fun String.asPriority(): Priority {
        return when(this) {
            Priority.STRENGTH.name -> Priority.STRENGTH
            Priority.HYPERTROPHY.name -> Priority.HYPERTROPHY
            else -> Priority.STRENGTH
        }
    }
}