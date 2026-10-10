package sa.edu.iau.cshj.studyplanner


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import sa.edu.iau.cshj.studyplanner.ui.theme.StudyPlannerTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

data class PlannerUiState(
    val greeting: String = "Hello, Student!",
    val totalTasks: Int = 7,
    val completedTasks: Int = 3
)


@Composable
fun PlannerHomeScreen(
    uiState: PlannerUiState,
    onMarkTaskDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(uiState.greeting)

        Text("${uiState.completedTasks} of ${uiState.totalTasks} done")

        Button(
            onClick = onMarkTaskDone,
            enabled = uiState.completedTasks < uiState.totalTasks
        ) {
            Text("Mark one task done")
        }
    }
}



@Preview(showBackground = true, name = "No tasks completed")
@Composable
private fun PlannerHomeEmptyPreview() {
    StudyPlannerTheme {
        PlannerHomeScreen(
            uiState = PlannerUiState(
                greeting = "Hello, Student!",
                totalTasks = 7,
                completedTasks = 0
            ),
            onMarkTaskDone = {}
        )
    }
}



@Preview(showBackground = true, name = "All tasks completed")
@Composable
private fun PlannerHomeCompletedPreview() {
    StudyPlannerTheme {
        PlannerHomeScreen(
            uiState = PlannerUiState(
                greeting = "Hello, Student!",
                totalTasks = 7,
                completedTasks = 7
            ),
            onMarkTaskDone = {}
        )
    }
}



@Composable
fun PlannerHomeRoute() {
    var uiState by remember {
        mutableStateOf(PlannerUiState())
    }

    PlannerHomeScreen(
        uiState = uiState,
        onMarkTaskDone = {
            if (uiState.completedTasks < uiState.totalTasks) {
                uiState = uiState.copy(
                    completedTasks = uiState.completedTasks + 1
                )
            }
        }
    )
}
