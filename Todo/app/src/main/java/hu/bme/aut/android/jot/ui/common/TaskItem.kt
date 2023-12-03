package hu.bme.aut.android.jot.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.jot.ui.model.PriorityUi
import kotlinx.datetime.LocalDate
import java.time.LocalDateTime

@Composable
fun TaskItem(
    name : String,
    weight : String
) {
    val shape = RoundedCornerShape(5.dp)
    Column{
        Row {
            Column {
                TextField(value = name, onValueChange = {})
                TextField(value = weight, onValueChange = {})
            }
        }

    }

}

@Composable
@Preview(showBackground = true)
fun TaskItem_Preview() {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val priorities = listOf(PriorityUi.Low, PriorityUi.Medium, PriorityUi.High)
    var selectedPriority by remember { mutableStateOf(priorities[0]) }

    val c = LocalDateTime.now()
    val pickedDate by remember { mutableStateOf(LocalDate(c.year,c.month,c.dayOfMonth)) }

    Box(Modifier.fillMaxSize()) {
        TaskItem(
            name = "abs",
            weight = "40kg"
        )
    }
}