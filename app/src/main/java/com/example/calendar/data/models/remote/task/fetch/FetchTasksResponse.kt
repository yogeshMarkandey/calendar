package com.example.calendar.data.models.remote.task.fetch


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FetchTasksResponse(
    @SerializedName("tasks")
    @Expose
    val taskDTOS: List<TaskDTO>?,

    @SerializedName("error")
    @Expose
    val error: String?,
)