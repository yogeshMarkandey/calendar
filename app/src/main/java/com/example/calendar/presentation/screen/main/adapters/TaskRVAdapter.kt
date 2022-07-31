package com.example.calendar.presentation.screen.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.calendar.R
import com.example.calendar.domain.models.Task
import com.example.calendar.presentation.screen.main.viewholders.TaskViewHolder

class TaskRVAdapter(private val onTaskCardClicked: OnTaskCardClicked) :
    ListAdapter<Task, TaskViewHolder>(
        callback
    ) {

    companion object {
        private val callback = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_card, parent, false)
        return TaskViewHolder(view, onTaskCardClicked)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val data = currentList[position]
        holder.bind(data)
    }

    fun getList(): List<Task> = currentList


    interface OnTaskCardClicked {
        fun onDeleteClicked(task: Task)
    }
}