package com.example.calendar.data.models.remote.task.fetch


import com.google.gson.annotations.SerializedName

data class FetchTasksRequest(
    @SerializedName("user_id")
    val userId: Int
)