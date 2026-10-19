
package sa.edu.iau.cshj.lab05.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import sa.edu.iau.cshj.lab05.ui.HomeRoute
import sa.edu.iau.cshj.lab05.ui.CourseListRoute
import sa.edu.iau.cshj.lab05.ui.CourseDetailRoute

@Composable
fun CourseApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeRoute(
                onCoursesClick = {
                    navController.navigate(CourseList)
                }
            )
        }

        composable<CourseList> {
            CourseListRoute(
                onCourseClick = { code ->
                    navController.navigate(CourseDetail(code))
                }
            )
        }

        composable<CourseDetail> { backStackEntry ->
            val detail: CourseDetail = backStackEntry.toRoute()

            CourseDetailRoute(
                code = detail.code,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
