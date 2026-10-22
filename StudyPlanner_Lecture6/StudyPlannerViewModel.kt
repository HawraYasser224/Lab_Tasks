package com.example.studyplanner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

// CSC 402 · Lecture 6 application — Study Planner
// Lecture slides: "Step 1 · The ViewModel owns the task list"

// The screen's whole state in one box (Lecture 4: val fields, each with a default).
data class StudyPlannerUiState(
    val tasks: List<String> = emptyList()
)

// The ViewModel keeps the task list alive when the phone is rotated.
class StudyPlannerViewModel : ViewModel() {

    // Everyone can READ uiState; only this class can CHANGE it (private set).
    var uiState by mutableStateOf(StudyPlannerUiState())
        private set

    fun addTask(title: String) {
        // TODO 1 — Add the task, following the rule "no empty tasks".
        //   • Only do something if title is not blank (hint: title.isNotBlank()).
        //   • Replace uiState with a NEW state made by copy():
        //     its tasks = the old tasks + title.trim()
        if (title.isNotBlank()) {
            uiState = uiState.copy(
                tasks = uiState.tasks + title.trim()
            )
        }
    }
}
