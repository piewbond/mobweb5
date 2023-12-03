package hu.bme.aut.android.jot.domain.model

import kotlinx.datetime.LocalDate

data class Excercise(
    val id: Int,
    val title: String,
    val excerciseType: ExcerciseType,
    val dueDate: LocalDate,
    val description: String
)