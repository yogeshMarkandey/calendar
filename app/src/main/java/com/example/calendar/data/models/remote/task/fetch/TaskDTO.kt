package com.example.calendar.data.models.remote.task.fetch


import com.example.calendar.data.models.db.task.TaskEntity
import com.example.calendar.domain.models.Task
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TaskDTO(
    @SerializedName("task_detail")
    @Expose
    val taskDetailDTO: TaskDetailDTO?,

    @SerializedName("task_id")
    @Expose
    val taskId: Int
)

fun TaskDTO.toTask() : Task {
    return Task(
        description = taskDetailDTO?.description ?: "",
        id = taskId,
        title = taskDetailDTO?.title ?: "",
        dueDate = taskDetailDTO?.dueDate ?: "2022-07-31",
        creationTime = taskDetailDTO?.creationTime ?: -1L,
        tag = taskDetailDTO?.tag ?: ""
    )
}

fun TaskDTO.toTaskEntity() : TaskEntity {
    return TaskEntity(
        id = taskId,
        description = taskDetailDTO?.description ?: "",
        title = taskDetailDTO?.title ?: "",
        dueDate = taskDetailDTO?.dueDate ?: "2022-07-31",
        tag = taskDetailDTO?.tag ?: "",
        creationTime = taskDetailDTO?.creationTime ?: -1L
    )
}