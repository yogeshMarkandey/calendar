package com.example.calendar.data.data_source

import com.example.calendar.data.models.task.add.AddTaskRequest
import com.example.calendar.data.models.task.add.AddTaskResponse
import com.example.calendar.data.models.task.delete.DeleteTaskRequest
import com.example.calendar.data.models.task.delete.DeleteTaskResponse
import com.example.calendar.data.models.task.fetch.FetchTasksRequest
import com.example.calendar.data.models.task.fetch.FetchTasksResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskAPIs{
    @POST(Endpoints.GET_TASK)
    suspend fun getTasks(
        @Body body: FetchTasksRequest
    ) : FetchTasksResponse

    @POST(Endpoints.DELETE_TASK)
    suspend fun deleteTasks(
        @Body body: DeleteTaskRequest
    ) : DeleteTaskResponse

    @POST(Endpoints.ADD_TASK)
    suspend fun addTasks(
        @Body body: AddTaskRequest
    ) : AddTaskResponse
}