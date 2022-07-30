package com.example.calendar.data.models.task.fetch


import com.google.gson.annotations.SerializedName

data class FetchTasksRequest(
    @SerializedName("user_id")
    val userId: Int
)