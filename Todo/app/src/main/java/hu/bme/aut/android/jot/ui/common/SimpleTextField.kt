package hu.bme.aut.android.jot.ui.common

import android.preference.PreferenceManager
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun SimpleTextField(
    title:String
) {
    val mContext = LocalContext.current

    val prefs = PreferenceManager.getDefaultSharedPreferences(mContext)

    val text = remember { mutableStateOf(TextFieldValue("")) }
    OutlinedTextField(value = text.value,
        onValueChange = {
            text.value = it
            prefs.edit().putString("newWeight" , text.value.text).commit()
                        },
        label = { Text(title) })
}