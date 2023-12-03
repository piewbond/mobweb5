package hu.bme.aut.android.jot.ui.common

import android.preference.PreferenceManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.jot.notification.RemindersManager


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTimePicker(
    timePickerOn : Boolean,
    onCloseDialog : () -> Unit
) {
    var selectedHour by remember {
        mutableIntStateOf(0) // or use  mutableStateOf(0)
    }

    var selectedMinute by remember {
        mutableIntStateOf(0) // or use  mutableStateOf(0)
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    val timePickerState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute
    )
    if (timePickerOn) {

        AlertDialog(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.background

                ),

            onDismissRequest = { showDialog = false
                onCloseDialog.invoke()
            }
        ) {
            val mContext = LocalContext.current

            val prefs = PreferenceManager.getDefaultSharedPreferences(mContext)


            Column(
                modifier = Modifier
                    .background(
                        color = Color.LightGray.copy(alpha = 0.3f)
                    )
                    .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // time picker
                TimeInput(state = timePickerState)

                // buttons
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    // dismiss button
                    TextButton(onClick = {
                            onCloseDialog.invoke()
                        }
                    ) {
                        Text(text = "Dismiss")
                    }

                    // confirm button
                    TextButton(
                        onClick = {
                            selectedHour = timePickerState.hour
                            selectedMinute = timePickerState.minute
                            onCloseDialog.invoke()
                            prefs.edit().putInt("selectedHour" , selectedHour).commit()
                            prefs.edit().putInt("selectedMinute" , selectedMinute).commit()
                            RemindersManager.startReminder(reminderTime = selectedHour.toString() + ":"+selectedMinute.toString(), context = mContext)
                        }
                    ) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}