package com.example.calendar.presentation.screen.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.data.models.task.add.AddTaskRequest
import com.example.calendar.data.models.task.delete.DeleteTaskRequest
import com.example.calendar.data.models.task.fetch.FetchTasksRequest
import com.example.calendar.domain.models.Task
import com.example.calendar.domain.repositories.TasksRepository
import com.example.calendar.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
) : ViewModel() {
    private val userId: Int = 7008

    private val _task = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _task

    fun getTasks() {
        val req = FetchTasksRequest(userId = userId)
        tasksRepository.fetchTask(req).onEach {
            when (it) {
                is State.Loading -> {

                }
                is State.Success -> {
                    _task.postValue(it.data?.ifEmpty { emptyList() })

                }
                is State.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    fun addTasks() {
        val random = Random()
        val req = AddTaskRequest(
            userId = userId, AddTaskRequest.TaskDetail(
                description = "Description : ${random.nextInt()}",
                title = "Title: ${random.nextInt()}"
            )
        )
        tasksRepository.addTask(req).onEach {
            when (it) {
                is State.Loading -> {

                }
                is State.Success -> {

                }
                is State.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteTasks() {
        val req = DeleteTaskRequest(userId = userId, taskId = 667)
        tasksRepository.deleteTask(req).onEach {
            when (it) {
                is State.Loading -> {

                }
                is State.Success -> {

                }
                is State.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }
}