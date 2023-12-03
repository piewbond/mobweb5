package hu.bme.aut.android.jot.data.converters

import androidx.room.TypeConverter
import hu.bme.aut.android.jot.domain.model.ExcerciseType
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
    fun ExcerciseType.asString(): String = this.name

    @TypeConverter
    fun String.asPriority(): ExcerciseType {
        return when(this) {
            ExcerciseType.STRENGTH.name -> ExcerciseType.STRENGTH
            ExcerciseType.HYPERTROPHY.name -> ExcerciseType.HYPERTROPHY
            else -> ExcerciseType.STRENGTH
        }
    }
}