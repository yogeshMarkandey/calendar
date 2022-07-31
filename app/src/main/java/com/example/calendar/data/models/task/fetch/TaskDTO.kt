package com.example.calendar.data.models.task.fetch


import com.example.calendar.domain.models.Task
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TaskDTO(
    @SerializedName("task_detail")
    @Expose
    val taskDetailDTO: TaskDetailDTO,

    @SerializedName("task_id")
    @Expose
    val taskId: Int
)

fun TaskDTO.toTask() : Task {
    return Task(
        description = taskDetailDTO.description,
        id = taskId,
        title = taskDetailDTO.title,
        dueDate = taskDetailDTO.dueDate
    )
}