
package com.example.studyplanner

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import com.example.studyplanner.ui.CourseRatingRoute
import com.example.studyplanner.ui.CampusNewsRoute

@Serializable
object Home

@Serializable
object Planner

@Serializable
data class CourseRating(val code: String)

@Serializable
object CampusNews

@Serializable
data class TaskDetails(val taskId: Int)

@Composable
fun HomeScreen(
    onRateClick: () -> Unit,
    onNewsClick: () -> Unit,
    onPlannerClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Study Planner")

        Button(onClick = onPlannerClick) {
            Text("Study Planner")
        }

        Button(onClick = onRateClick) {
            Text("Rate CSC 402")
        }

        Button(onClick = onNewsClick) {
            Text("Campus news")
        }
    }
}

@Composable
fun CourseApp(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val plannerViewModel: StudyPlannerViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = modifier
    ) {

        composable<Home> {
            HomeScreen(
                onPlannerClick = {
                    navController.navigate(Planner)
                },
                onRateClick = {
                    navController.navigate(CourseRating("CSC 402"))
                },
                onNewsClick = {
                    navController.navigate(CampusNews)
                }
            )
        }

        composable<Planner> {
            StudyPlannerRoute(
                viewModel = plannerViewModel,
                onTaskClick = { taskId ->
                    navController.navigate(TaskDetails(taskId))
                }
            )
        }

        composable<TaskDetails> { entry ->
            val taskId = entry.toRoute<TaskDetails>().taskId

            val task = plannerViewModel.uiState.tasks
                .firstOrNull { it.id == taskId }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text("Back")
                }

                if (task != null) {
                    Text("Task Details")
                    Text("Title: ${task.title}")
                    Text("Course: ${task.course}")
                    Text("Completed: ${task.isDone}")
                } else {
                    Text("Task not found")
                }
            }
        }

        composable<CourseRating> { entry ->
            CourseRatingRoute(
                code = entry.toRoute<CourseRating>().code
            )
        }

        composable<CampusNews> {
            CampusNewsRoute(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
