
package sa.edu.iau.cshj.studyplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import sa.edu.iau.cshj.studyplanner.ui.theme.StudyPlannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            StudyPlannerTheme {
                StudyPlannerApp()
            }
        }
    }
}

@Composable
fun StudyPlannerApp() {
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
        startDestination = PlannerHome
    ) {
        composable<PlannerHome> {
            PlannerHomeScreen(
                uiState = uiState,
                onMarkTaskDone = {
                    if (uiState.completedTasks < uiState.totalTasks) {
                        uiState = uiState.copy(
                            completedTasks = uiState.completedTasks + 1
                        )
                    }
                },
                onTaskClick = { title ->
                    navController.navigate(TaskDetail(title))
                }
            )
        }

        composable<TaskDetail> { backStackEntry ->
            val task = backStackEntry.toRoute<TaskDetail>()

            TaskDetailRoute(
                title = task.title,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
