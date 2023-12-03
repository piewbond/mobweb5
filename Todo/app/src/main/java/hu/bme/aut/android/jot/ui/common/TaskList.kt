package hu.bme.aut.android.jot.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.jot.domain.model.Task

@Composable
fun TaskList(tasks: SnapshotStateList<Task>) {

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                TaskItem(name = "abs", weight = "366kd")
                Spacer(modifier = Modifier.height(10.dp))
            }
            items (tasks) {
                    item ->
                TaskItem(name = item.title, weight = item.weight)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

}