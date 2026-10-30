
package com.example.studyplanner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class StudyPlannerUiState(
    val tasks: List<StudyTask> = listOf(
        StudyTask(1, "Read Chapter 1", "Biostatistics"),
        StudyTask(2, "Review Lecture 2", "Nursing"),
        StudyTask(3, "Complete Lab 5", "CSC 402"),
        StudyTask(4, "Study Immunology", "Microbiology"),
        StudyTask(5, "Prepare Assignment", "Research")
    )
)

class StudyPlannerViewModel : ViewModel() {

    var uiState by mutableStateOf(StudyPlannerUiState())
        private set

    fun addTask(title: String) {
        if (title.isNotBlank()) {
            val newId = (uiState.tasks.maxOfOrNull { it.id } ?: 0) + 1

            uiState = uiState.copy(
                tasks = uiState.tasks + StudyTask(
                    id = newId,
                    title = title.trim(),
                    course = "General"
                )
            )
        }
    }

    fun updateTaskDone(id: Int, isDone: Boolean) {
        uiState = uiState.copy(
            tasks = uiState.tasks.map { task ->
                if (task.id == id) {
                    task.copy(isDone = isDone)
                } else {
                    task
                }
            }
        )
    }
}
