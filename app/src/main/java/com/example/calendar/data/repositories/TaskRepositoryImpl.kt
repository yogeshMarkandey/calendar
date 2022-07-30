package com.example.calendar.data.repositories

import com.example.calendar.data.data_source.TaskAPIs
import com.example.calendar.domain.repositories.TasksRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val taskAPIs: TaskAPIs
) : TasksRepository {
    override fun addTask() {
        TODO("Not yet implemented")
    }

    override fun fetchTask() {
        TODO("Not yet implemented")
    }

    override fun deleteTask() {
        TODO("Not yet implemented")
    }
}