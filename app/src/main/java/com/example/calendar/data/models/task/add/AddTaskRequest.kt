package com.example.calendar.data.models.task.add


import com.google.gson.annotations.SerializedName

data class AddTaskRequest(
    @SerializedName("user_id")
    val userId: Int
)