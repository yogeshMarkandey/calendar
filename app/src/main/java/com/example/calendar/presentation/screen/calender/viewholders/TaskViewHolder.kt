package com.example.calendar.presentation.screen.calender.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.domain.models.Task
import com.example.calendar.presentation.screen.calender.adapters.TaskRVAdapter

class TaskViewHolder(
    itemView: View,
    private val onTaskCardClicked: TaskRVAdapter.OnTaskCardClicked
) : RecyclerView.ViewHolder(itemView) {

    private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
    private val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)

    fun bind(task: Task) {
        itemView.setOnClickListener {
            onTaskCardClicked.onDeleteClicked(task)
        }
        tvTitle?.text = task.title
        tvDescription?.text = task.description
    }
}