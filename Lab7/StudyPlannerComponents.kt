package com.example.studyplanner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Task count
@Composable
fun TaskCount(count: Int) {
    Text(
        "You have $count study tasks",
        style = MaterialTheme.typography.titleMedium
    )
}

// Add task row
@Composable
fun AddTaskRow(
    text: String,
    onTextChange: (String) -> Unit,
    onAdd: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            label = { Text("New task") },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(onClick = onAdd) {
            Text("Add")
        }
    }
}

// Reusable Task Item
@Composable
fun TaskItem(
    task: StudyTask,
    onTaskClick: (Int) -> Unit,
    onDoneChange: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onTaskClick(task.id)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isDone,
            onCheckedChange = { checked ->
                onDoneChange(task.id, checked)
            }
        )

        Column {
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = task.course,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

// Preview
@Preview(showBackground = true)
@Composable
fun AddTaskRowPreview() {
    AddTaskRow(
        text = "Read Ch. 2",
        onTextChange = {},
        onAdd = {}
    )
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    TaskItem(
        task = StudyTask(
            id = 1,
            title = "Complete Chapter 3",
            course = "Biostatistics"
        ),
        onTaskClick = {},
        onDoneChange = { _, _ -> }
    )
}