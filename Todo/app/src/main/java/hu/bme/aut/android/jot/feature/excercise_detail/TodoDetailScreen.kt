package hu.bme.aut.android.jot.feature.excercise_detail

import android.preference.PreferenceManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.jot.ui.common.CreateTaskDialog
import hu.bme.aut.android.jot.ui.common.ExcerciseAppBar
import hu.bme.aut.android.jot.ui.model.asExcercise
import hu.bme.aut.android.jot.ui.model.toUiText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    onNavigateBack: () -> Unit,
    viewModel: TodoDetailViewModel = viewModel(factory = TodoDetailViewModel.Factory)
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            if (state is TodoDetailState.Result) {
                ExcerciseAppBar(

                    title = state.todo.title,
                    onNavigateBack = onNavigateBack,
                    onDelete = {
                        viewModel.onDelete()
                    }
                )
            }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is TodoDetailState.Loading -> CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
                is TodoDetailState.Error -> Text(
                    text = state.error.toUiText().asString(context)
                )
                is TodoDetailState.Result -> {
                    val todo = state.todo

                    CreateTaskDialog(timePickerOn = showDialog, onCloseDialog = {
                        showDialog = false
                    }, onConfirm = {

                        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
                        val text = prefs.getString("newWeight","")

                        if (text != null) {
                            todo.description = text
                        }
                        viewModel.onEdit(todo.asExcercise())
                    })

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .height(TextFieldDefaults.MinHeight)
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(size = 5.dp))
                                .background(color = Color.White),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Circle,
                                contentDescription = null,
                                tint = todo.priority.color,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                modifier = Modifier
                                    .weight(weight = 8f),
                                text = stringResource(id = todo.priority.title),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                        Text(
                            todo.description, fontSize = 30.sp
                        )
                        Button(
                            onClick = {
                                showDialog = true

                        }) {
                            Text(text = "Update weight")
                        }
                    }
                }

                else -> {}
            }
        }
    }
}