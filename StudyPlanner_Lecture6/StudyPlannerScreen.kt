package com.example.studyplanner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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

// CSC 402 · Lecture 6 application — Study Planner
// Lecture slides: "Step 2 · The Route connects the ViewModel"
//                 "Step 3 · The Screen draws the state"
// Naming (Lecture 4): StudyPlannerRoute OWNS the state, StudyPlannerScreen only DRAWS.

// ───────────────────────── The Route: owns the state ─────────────────────────
@Composable
fun StudyPlannerRoute(viewModel: StudyPlannerViewModel = viewModel()) {
    var draft by rememberSaveable { mutableStateOf("") }

    StudyPlannerScreen(
        uiState = viewModel.uiState,            // state flows DOWN
        draft = draft,
        onDraftChange = {
            draft = it
        },
        onAddTask = {
            viewModel.addTask(draft)
            draft = ""
        }
    )
}

// ───────────────────────── The Screen: stateless ─────────────────────────────
@Composable
fun StudyPlannerScreen(
    uiState: StudyPlannerUiState,
    draft: String,
    onDraftChange: (String) -> Unit,
    onAddTask: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        TaskCount(count = uiState.tasks.size)

// TODO 5 - Show AddTaskRow here. Hand it:
// text = draft, onTextChange = onDraftChange, onAdd = onAddTask
        AddTaskRow(
            text = draft,
            onTextChange = onDraftChange,
            onAdd = onAddTask
        )
        uiState.tasks.forEach { task ->
            Text("• $task")
        }
    }
}

// ───────────────────────── Preview: no app, no ViewModel needed ──────────────
@Preview(showBackground = true)
@Composable
fun StudyPlannerScreenPreview() {
    StudyPlannerScreen(
        uiState = StudyPlannerUiState(tasks = listOf("Read Ch. 2", "Finish Lab 6")),
        draft = "",
        onDraftChange = {},
        onAddTask = {}
    )
}
