package com.example.calendar.presentation.screen.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.calendar.R
import com.example.calendar.domain.models.Task

class TaskRVAdapter : ListAdapter<Task, TaskViewHolder>(callback) {

    companion object{
        private val callback = object: DiffUtil.ItemCallback<Task> (){
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
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val data = currentList[position]
        holder.bind(data)
    }

    fun getList() : List<Task> = currentList
}