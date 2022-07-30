package com.example.calendar.data.models.task.delete


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DeleteTaskRequest(
    @SerializedName("task_id")
    @Expose
    val taskId: Int,

    @SerializedName("user_id")
    @Expose
    val userId: Int
)