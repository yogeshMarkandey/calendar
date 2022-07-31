package com.example.calendar.domain.repositories

import com.example.calendar.data.models.db.task.TaskEntity
import com.example.calendar.data.models.remote.task.add.AddTaskRequest
import com.example.calendar.data.models.remote.task.delete.DeleteTaskRequest
import com.example.calendar.data.models.remote.task.fetch.FetchTasksRequest
import com.example.calendar.domain.models.Task
import com.example.calendar.utils.State
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun addTask(body: AddTaskRequest) : Flow<State<String>>
    fun fetchTask(body: FetchTasksRequest) : Flow<State<List<Task>>>
    fun deleteTask(body: DeleteTaskRequest) : Flow<State<String>>
    fun getAllTasks() : Flow<List<TaskEntity>>
}