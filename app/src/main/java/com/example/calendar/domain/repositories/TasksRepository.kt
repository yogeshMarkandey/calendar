package com.example.calendar.domain.repositories

interface TasksRepository {
    fun addTask()
    fun fetchTask()
    fun deleteTask()
}