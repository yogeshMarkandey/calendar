package com.example.calendar.domain.models

data class Task(
    val description: String,
    val title: String,
    val id: Int,
    val dueDate: String,
    val creationTime: Long,
    val tag: String
)

