package hu.bme.aut.android.jot.ui.common

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun SimpleTextField(
    title:String
) {
    val text = remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(value = text.value,
        onValueChange = { text.value = it },
        label = { Text(title) })
}