package hu.bme.aut.android.jot.ui.model

import hu.bme.aut.android.jot.domain.model.Excercise
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import java.time.LocalDateTime

data class ExcerciseUi(
    val id: Int = 0,
    val title: String = "",
    val priority: PriorityUi = PriorityUi.Hypertrophy,
    val dueDate: String = LocalDate(
        LocalDateTime.now().year,
        LocalDateTime.now().monthValue,
        LocalDateTime.now().dayOfMonth
    ).toString(),
    var description: String = ""
)

fun Excercise.asExcerciseUi(): ExcerciseUi = ExcerciseUi(
    id = id,
    title = title,
    priority = priority.asPriorityUi(),
    dueDate = dueDate.toString(),
    description = description
)

fun ExcerciseUi.asExcercise(): Excercise = Excercise(
    id = id,
    title = title,
    priority = priority.asPriority(),
    dueDate = dueDate.toLocalDate(),
    description = description
)