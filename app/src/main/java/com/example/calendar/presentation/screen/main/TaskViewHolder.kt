package com.example.calendar.presentation.screen.main

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.domain.models.Task

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
    private val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)

    fun bind(task: Task) {
        tvTitle?.text = task.title
        tvDescription?.text = task.description
    }
}