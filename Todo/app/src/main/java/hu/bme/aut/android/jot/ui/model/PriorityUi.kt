package hu.bme.aut.android.jot.ui.model

import androidx.compose.ui.graphics.Color
import hu.bme.aut.android.jot.R
import hu.bme.aut.android.jot.domain.model.ExcerciseType

enum class PriorityUi(
    val title: Int,
    val color: Color
) {

    Hypertrophy(
        title = R.string.priority_title_low,
        color = Color(0xFF8BC34A)
    ),
    Strength(
        title = R.string.priority_title_medium,
        color = Color(0xFFFFC107)
    )
}

fun PriorityUi.asPriority(): ExcerciseType {
    return when(this) {
        PriorityUi.Hypertrophy -> ExcerciseType.HYPERTROPHY
        PriorityUi.Strength -> ExcerciseType.STRENGTH }
}

fun ExcerciseType.asPriorityUi(): PriorityUi {
    return when(this) {
        ExcerciseType.HYPERTROPHY -> PriorityUi.Hypertrophy
        ExcerciseType.STRENGTH -> PriorityUi.Strength
    }
}