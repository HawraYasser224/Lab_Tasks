package sa.edu.iau.cshj.studyplanner



import kotlinx.serialization.Serializable

@Serializable
object PlannerHome

@Serializable
data class TaskDetail(val title: String)
