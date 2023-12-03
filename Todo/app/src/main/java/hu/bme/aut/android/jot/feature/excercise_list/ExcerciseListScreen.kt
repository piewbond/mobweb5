package hu.bme.aut.android.jot.feature.excercise_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.jot.R
import hu.bme.aut.android.jot.ui.common.HomeTopAppbar
import hu.bme.aut.android.jot.ui.common.NotificationTimePicker
import hu.bme.aut.android.jot.ui.model.toUiText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExcerciseListScreen(
    onListItemClick: (Int) -> Unit,
    onFabClick: () -> Unit,
    viewModel: ExcerciseListViewModel = viewModel(factory = ExcerciseListViewModel.Factory),
    onThemeUpdated: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadExcercises()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = onFabClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        topBar = {
            HomeTopAppbar(
                title = stringResource(id = R.string.app_bar_title_list_excercise),
                onThemeUpdated = onThemeUpdated,
                onSetNotification = {
                    showDialog = true
                }
            )
        }
    ) {

        NotificationTimePicker(timePickerOn = showDialog, onCloseDialog = {
                showDialog = false
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(
                    color = MaterialTheme.colorScheme.background
                ),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is ExcerciseListState.Loading -> CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
                is ExcerciseListState.Error -> Text(
                    text = state.error.toUiText().asString(context)
                )
                is ExcerciseListState.Result -> {
                    if (state.excerciseList.isEmpty()) {
                        Text(text = stringResource(id = R.string.text_empty_excercise_list))
                    } else {

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            items(state.excerciseList, key = { excerciseUi -> excerciseUi.id }) { excercise ->
                                ListItem(
                                    headlineContent = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                imageVector = Icons.Default.Circle,
                                                contentDescription = null,
                                                tint = excercise.priority.color,
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .padding(
                                                        end = 8.dp,
                                                        top = 8.dp,
                                                        bottom = 8.dp
                                                    ),
                                            )
                                            Text(text = excercise.title)
                                        }
                                    },
                                    modifier = Modifier.clickable(onClick = {
                                        onListItemClick(
                                            excercise.id
                                        )
                                    })
                                )
                                if (state.excerciseList.last() != excercise) {
                                    Divider(
                                        thickness = 2.dp,
                                        color = MaterialTheme.colorScheme.secondaryContainer
                                    )
                                }
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}