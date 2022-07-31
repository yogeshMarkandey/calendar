package com.example.calendar.data.models.db.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.calendar.data.util.DatabaseConstants.TASK_TABLE
import com.example.calendar.domain.models.Task

@Entity(tableName = TASK_TABLE)
data class TaskEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    val title: String,
    val description: String,
    @ColumnInfo(name = "due_date")
    val dueDate: String,
    val tag: String,
    val creationTime: Long
)

fun TaskEntity.toTask() : Task{
    return Task(
        description = description,
        title = title,
        id = id,
        dueDate = dueDate
    )
}