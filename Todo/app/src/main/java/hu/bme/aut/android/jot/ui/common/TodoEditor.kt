package hu.bme.aut.android.jot.ui.common

import android.graphics.Paint.Align
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.jot.R
import hu.bme.aut.android.jot.ui.model.PriorityUi
import kotlinx.datetime.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TodoEditor(
    titleValue: String,
    titleOnValueChange: (String) -> Unit,
    descriptionValue: String,
    descriptionOnValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    priorities: List<PriorityUi> = listOf(PriorityUi.Low, PriorityUi.Medium, PriorityUi.High),
    selectedPriority: PriorityUi,
    onPrioritySelected: (PriorityUi) -> Unit,
    enabled: Boolean = true,
) {
    val fraction = 0.95f

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        if (enabled) {
            NormalTextField(
                value = titleValue,
                label = stringResource(id = R.string.textfield_label_title),
                onValueChange = titleOnValueChange,
                singleLine = true,
                onDone = { keyboardController?.hide()  },
                modifier = Modifier
                    .fillMaxWidth(fraction)
                    .padding(top = 5.dp)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        PriorityDropDown(
            priorities = priorities,
            selectedPriority = selectedPriority,
            onPrioritySelected = onPrioritySelected,
            modifier = Modifier
                .fillMaxWidth(fraction),
            enabled = enabled
        )
        Spacer(modifier = Modifier.height(5.dp))

        Button( modifier = Modifier
            .padding(bottom = 5.dp)
            .padding(start = 10.dp)
            .align(Alignment.Start),
            enabled = enabled,
            onClick = {}
        ) {
            Icon(Icons.Default.AddCircle, contentDescription = null)
            Spacer(modifier = Modifier.width(10.dp))
            Text("Add new Task")
        }
        Spacer(modifier = Modifier.height(5.dp))
        NormalTextField(
            value = descriptionValue,
            label = stringResource(id = R.string.textfield_label_description),
            onValueChange = descriptionOnValueChange,
            singleLine = false,
            onDone = { keyboardController?.hide() },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(fraction)
                .padding(bottom = 5.dp),
            enabled = enabled
        )

    }
}

@Composable
@Preview(showBackground = true)
fun TodoEditor_Preview() {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val priorities = listOf(PriorityUi.Low, PriorityUi.Medium, PriorityUi.High)
    var selectedPriority by remember { mutableStateOf(priorities[0]) }

    val c = LocalDateTime.now()
    val pickedDate by remember { mutableStateOf(LocalDate(c.year,c.month,c.dayOfMonth)) }

    Box(Modifier.fillMaxSize()) {
        TodoEditor(
            titleValue = title,
            titleOnValueChange = { title = it },
            descriptionValue = description,
            descriptionOnValueChange = { description = it },
            priorities = priorities,
            selectedPriority = selectedPriority,
            onPrioritySelected = { selectedPriority = it }
        )
    }
}