package com.example.calendar.data.data_source.room

import androidx.room.*
import com.example.calendar.data.models.db.task.TaskEntity
import com.example.calendar.data.util.DatabaseConstants.TASK_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Transaction
    @Query("SELECT * FROM $TASK_TABLE")
    fun getAllTasks() : Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: TaskEntity)

    @Transaction
    @Query("DELETE FROM $TASK_TABLE WHERE id IS :taskId")
    suspend fun deleteTaskWithId(taskId: Int)
}