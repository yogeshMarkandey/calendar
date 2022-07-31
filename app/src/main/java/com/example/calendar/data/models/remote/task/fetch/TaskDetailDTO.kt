package com.example.calendar.data.models.remote.task.fetch


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TaskDetailDTO(
    @SerializedName("description")
    @Expose
    val description: String,

    @Expose
    @SerializedName("title")
    val title: String,

    @Expose
    @SerializedName("due_date")
    val dueDate: String,

    @Expose
    @SerializedName("creation_time")
    val creationTime: Long,

    @Expose
    @SerializedName("tag")
    val tag: String,
)