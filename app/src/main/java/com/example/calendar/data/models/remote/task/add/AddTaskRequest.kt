package com.example.calendar.data.models.remote.task.add


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddTaskRequest(
    @SerializedName("user_id")
    @Expose
    val userId: Int,

    @SerializedName("task")
    @Expose
    val task: TaskDetail

) {
    data class TaskDetail(
        @SerializedName("description")
        @Expose
        val description: String,

        @Expose
        @SerializedName("title")
        val title: String,

        @Expose
        @SerializedName("due_date")
        val dueDate: String,
    )
}