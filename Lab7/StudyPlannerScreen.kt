
package com.example.studyplanner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

// Route owns the state and handles navigation.
@Composable
fun StudyPlannerRoute(
    onTaskClick: (Int) -> Unit,
    viewModel: StudyPlannerViewModel = viewModel()
) {
    var draft by rememberSaveable {
        mutableStateOf("")
    }

    StudyPlannerScreen(
        uiState = viewModel.uiState,
        draft = draft,

        onDraftChange = {
            draft = it
        },

        onAddTask = {
            viewModel.addTask(draft)
            draft = ""
        },

        onTaskClick = onTaskClick,

        onDoneChange = { id, checked ->
            viewModel.updateTaskDone(id, checked)
        }
    )
}

// Screen draws the state.
@Composable
fun StudyPlannerScreen(
    uiState: StudyPlannerUiState,
    draft: String,
    onDraftChange: (String) -> Unit,
    onAddTask: () -> Unit,
    onTaskClick: (Int) -> Unit,
    onDoneChange: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        // Task count
        TaskCount(count = uiState.tasks.size)

        // Progress
        Text(
            text = "${uiState.tasks.count { it.isDone }} of ${uiState.tasks.size} done"
        )

        // Add task
        AddTaskRow(
            text = draft,
            onTextChange = onDraftChange,
            onAdd = onAddTask
        )

        // Task list
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(
                items = uiState.tasks,
                key = { it.id }
            ) { task ->

                TaskItem(
                    task = task,
                    onTaskClick = onTaskClick,
                    onDoneChange = onDoneChange
                )
            }
        }
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun StudyPlannerScreenPreview() {
    StudyPlannerScreen(
        uiState = StudyPlannerUiState(),
        draft = "",
        onDraftChange = {},
        onAddTask = {},
        onTaskClick = {},
        onDoneChange = { _, _ -> }
    )
}
