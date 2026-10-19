
package sa.edu.iau.cshj.studyplanner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sa.edu.iau.cshj.studyplanner.ui.theme.StudyPlannerTheme
import android.net.Uri
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

data class PlannerUiState(
    val greeting: String = "Hello, Student!",
    val totalTasks: Int = 7,
    val completedTasks: Int = 3,
    val tasks: List<String> = emptyList()
)

@Composable
fun PlannerHomeScreen(
    uiState: PlannerUiState,
    onMarkTaskDone: () -> Unit,
    onTaskClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(uiState.greeting)

        Text(
            "${uiState.completedTasks} of ${uiState.totalTasks} done"
        )

        uiState.tasks.forEach { task ->
            Button(
                onClick = { onTaskClick(task) }
            ) {
                Text(task)
            }
        }

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
                completedTasks = 0,
                tasks = listOf(
                    "Complete Lab 5",
                    "Review lecture notes",
                    "Prepare for exam"
                )
            ),
            onMarkTaskDone = {},
            onTaskClick = {}
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
                completedTasks = 7,
                tasks = listOf(
                    "Complete Lab 5",
                    "Review lecture notes",
                    "Prepare for exam"
                )
            ),
            onMarkTaskDone = {},
            onTaskClick = {}
        )
    }
}

@Composable
fun PlannerHomeRoute() {
    val navController = rememberNavController()

    var uiState by remember {
        mutableStateOf(
            PlannerUiState(
                tasks = listOf(
                    "Complete Lab 5",
                    "Review lecture notes",
                    "Prepare for exam"
                )
            )
        )
    }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            PlannerHomeScreen(
                uiState = uiState,
                onMarkTaskDone = {
                    if (uiState.completedTasks < uiState.totalTasks) {
                        uiState = uiState.copy(
                            completedTasks = uiState.completedTasks + 1
                        )
                    }
                },
                onTaskClick = { task ->
                    navController.navigate(
                        "details/${Uri.encode(task)}"
                    )
                }
            )
        }

        composable(
            route = "details/{task}",
            arguments = listOf(
                navArgument("task") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val task = backStackEntry.arguments
                ?.getString("task")
                .orEmpty()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Task Details")
                Text(task)

                Button(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Text("Back")
                }
            }
        }
    }
}

