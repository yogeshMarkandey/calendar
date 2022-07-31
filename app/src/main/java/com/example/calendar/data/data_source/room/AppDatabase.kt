package com.example.calendar.data.data_source.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.calendar.data.models.db.task.TaskEntity


@Database(
    entities = [TaskEntity::class],
    version = 2
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao() : TaskDao
}