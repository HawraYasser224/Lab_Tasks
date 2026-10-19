package sa.edu.iau.cshj.studyplanner

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sa.edu.iau.cshj.studyplanner.ui.theme.StudyPlannerTheme



data class TaskDetailUiState(
    val title: String
)

@Composable
fun TaskDetailScreen(
    uiState: TaskDetailUiState,
    onShareClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Task Details")

        Text(uiState.title)

        Button(onClick = onShareClick) {
            Text("Share")
        }

        Button(onClick = onBackClick) {
            Text("Back")
        }
    }
}

@Preview(showBackground = true, name = "Lab Task")
@Composable
private fun TaskDetailLabPreview() {
    StudyPlannerTheme {
        TaskDetailScreen(
            uiState = TaskDetailUiState(
                title = "Complete Lab 5"
            ),
            onShareClick = {},
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Exam Task")
@Composable
private fun TaskDetailExamPreview() {
    StudyPlannerTheme {
        TaskDetailScreen(
            uiState = TaskDetailUiState(
                title = "Prepare for exam"
            ),
            onShareClick = {},
            onBackClick = {}
        )
    }
}


@Composable
fun TaskDetailRoute(
    title: String,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    TaskDetailScreen(
        uiState = TaskDetailUiState(title = title),
        onShareClick = {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Today I'm studying: $title"
                )
            }

            context.startActivity(
                Intent.createChooser(intent, "Share task")
            )
        },
        onBackClick = onBackClick
    )
}
