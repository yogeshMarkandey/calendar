package com.example.calendar.data.repositories

import com.example.calendar.data.data_source.TaskAPIs
import com.example.calendar.data.models.task.add.AddTaskRequest
import com.example.calendar.data.models.task.delete.DeleteTaskRequest
import com.example.calendar.data.models.task.fetch.FetchTasksRequest
import com.example.calendar.data.models.task.fetch.toTask
import com.example.calendar.domain.models.Task
import com.example.calendar.domain.repositories.TasksRepository
import com.example.calendar.utils.State
import com.example.calendar.utils.StateError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskAPIs: TaskAPIs
) : TasksRepository {
    override fun addTask(body: AddTaskRequest): Flow<State<String>> {
        return flow {
            try {
                emit(State.Loading)
                val response = taskAPIs.addTasks(body)
                if (!response.error.isNullOrBlank()) {
                    emit(State.Error(StateError(message = response.error)))
                    return@flow
                }

                if (response.status == "Success") {
                    emit(State.Success(data = response.status.toString()))
                } else {
                    emit(State.Error(StateError(message = response.error ?: "")))
                }
            } catch (e: HttpException) {
                emit(
                    State.Error(
                        StateError(
                            message = e.message ?: e.localizedMessage,
                            errorCode = e.code()
                        )
                    )
                )
            } catch (e: IOException) {
                emit(State.Error(StateError(message = e.message ?: e.localizedMessage)))
            }
        }
    }

    override fun fetchTask(body: FetchTasksRequest): Flow<State<List<Task>>> {
        return flow {
            try {
                emit(State.Loading)
                val response = taskAPIs.getTasks(body)
                if (!response.error.isNullOrBlank()) {
                    emit(State.Error(StateError(message = response.error)))
                    return@flow
                }
                if (response.taskDTOS != null) {
                    emit(State.Success(data = response.taskDTOS.map { it.toTask() }))
                } else {
                    emit(State.Error(StateError(message = response.error ?: "")))
                }
            } catch (e: HttpException) {
                emit(
                    State.Error(
                        StateError(
                            message = e.message ?: e.localizedMessage,
                            errorCode = e.code()
                        )
                    )
                )
            } catch (e: IOException) {
                emit(State.Error(StateError(message = e.message ?: e.localizedMessage)))
            }
        }
    }

    override fun deleteTask(body: DeleteTaskRequest): Flow<State<String>> {
        return flow {
            try {
                emit(State.Loading)
                val response = taskAPIs.deleteTasks(body)
                if (!response.error.isNullOrBlank()) {
                    emit(State.Error(StateError(message = response.error)))
                    return@flow
                }
                if (response.status == "Success") {
                    emit(State.Success(data = response.status.toString()))
                } else {
                    emit(State.Error(StateError(message = response.error ?: "")))
                }
            } catch (e: HttpException) {
                emit(
                    State.Error(
                        StateError(
                            message = e.message ?: e.localizedMessage,
                            errorCode = e.code()
                        )
                    )
                )
            } catch (e: IOException) {
                emit(State.Error(StateError(message = e.message ?: e.localizedMessage)))
            }
        }
    }
}