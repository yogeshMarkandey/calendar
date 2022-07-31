package com.example.calendar.data.models.remote.task.delete


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DeleteTaskResponse(
    @SerializedName("error")
    @Expose
    val error: String?,

    @SerializedName("status")
    @Expose
    val status: String?
)