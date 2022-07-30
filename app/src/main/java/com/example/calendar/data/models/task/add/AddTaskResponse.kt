package com.example.calendar.data.models.task.add

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddTaskResponse(
    @SerializedName("error")
    @Expose
    val error: String?,

    @SerializedName("status")
    @Expose
    val status: String?
)