package hu.bme.aut.android.jot.feature.excercise_create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.bme.aut.android.jot.R
import hu.bme.aut.android.jot.domain.model.ExcerciseType
import hu.bme.aut.android.jot.ui.common.ExcerciseAppBar
import hu.bme.aut.android.jot.ui.common.ExcerciseEditor
import hu.bme.aut.android.jot.ui.model.asPriorityUi
import kotlinx.coroutines.launch

@Composable
fun ExcerciseCreateScreen(
    onNavigateBack: () -> Unit,
    viewModel: ExcerciseCreateViewModel = viewModel(factory = ExcerciseCreateViewModel.Factory)
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }
    val hostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when(uiEvent) {
                is ExcerciseCreateUiEvent.Success -> { onNavigateBack() }
                is ExcerciseCreateUiEvent.Failure -> {
                    scope.launch {
                        hostState.showSnackbar(uiEvent.error.asString(context))
                    }
                }

                else -> {}
            }
        }
    }



    Scaffold(
        snackbarHost = { SnackbarHost(hostState) },
        topBar = {
            ExcerciseAppBar(
                title = stringResource(id = R.string.app_bar_title_create_excercise),
                onNavigateBack = onNavigateBack,
                onDelete = {},
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { viewModel.onEvent(ExcerciseCreateEvent.SaveExcercise) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null)
            }
        }
    ) {
            padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            ExcerciseEditor(
                titleValue = state.excerciseUi.title,
                titleOnValueChange = { viewModel.onEvent(ExcerciseCreateEvent.ChangeTitle(it)) },
                descriptionValue = state.excerciseUi.description,
                descriptionOnValueChange = { viewModel.onEvent(ExcerciseCreateEvent.ChangeDescription(it)) },
                priorities = ExcerciseType.values().map { it.asPriorityUi() },
                selectedPriority = state.excerciseUi.priority,
                onPrioritySelected = { viewModel.onEvent(ExcerciseCreateEvent.SelectPriority(it)) },
                modifier = Modifier.background(MaterialTheme.colorScheme.background),
                onCreateTaskPressed = {showDialog = !showDialog}
            )
        }
    }
}