package com.example.calendar.presentation.screen.calender.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.domain.models.Task
import com.example.calendar.presentation.screen.calender.adapters.TaskRVAdapter
import com.example.calendar.presentation.util.ColorSchemeType
import com.example.calendar.presentation.util.ViewHelper
import com.example.calendar.presentation.util.ViewHelper.getColorsFromResource

class TaskViewHolder(
    itemView: View,
    private val onTaskCardClicked: TaskRVAdapter.OnTaskCardClicked
) : RecyclerView.ViewHolder(itemView) {

    private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
    private val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
    private val sideView = itemView.findViewById<View>(R.id.sideView)
    private val rootLayout = itemView.findViewById<CardView>(R.id.rootCardView)
    private val ivClose = itemView.findViewById<ImageView>(R.id.ivClose)

    fun bind(task: Task) {
        setupColorScheme(getColorScheme(taskId = task.id))
        ivClose.setOnClickListener {
            onTaskCardClicked.onDeleteClicked(task)
        }
        tvTitle?.text = task.title
        tvDescription?.text = task.description
    }

    private fun getColorScheme(taskId: Int) : ColorSchemeType{
       return when(taskId%3){
            0 -> ColorSchemeType.Yellow
            1 -> ColorSchemeType.Green
            2 -> ColorSchemeType.Blue
            else -> ColorSchemeType.Yellow
        }
    }

    private fun setupColorScheme(type: ColorSchemeType){
        when(type){
            ColorSchemeType.Yellow -> {
                rootLayout.setCardBackgroundColor(getColorsFromResource(itemView.context, R.color.yellow_light))
                sideView.setBackgroundColor(getColorsFromResource(itemView.context, R.color.yellow))
                tvTitle.setTextColor(getColorsFromResource(itemView.context, R.color.yellow))
                tvDescription.setTextColor(getColorsFromResource(itemView.context, R.color.yellow_medium))
                ivClose.setColorFilter(getColorsFromResource(itemView.context, R.color.yellow))
            }
            ColorSchemeType.Green -> {
                rootLayout.setCardBackgroundColor(getColorsFromResource(itemView.context, R.color.green_light))
                sideView.setBackgroundColor(getColorsFromResource(itemView.context, R.color.green))
                tvTitle.setTextColor(getColorsFromResource(itemView.context, R.color.green))
                tvDescription.setTextColor(getColorsFromResource(itemView.context, R.color.green_medium))
                ivClose.setColorFilter(getColorsFromResource(itemView.context, R.color.green))
            }
            ColorSchemeType.Blue -> {
                rootLayout.setCardBackgroundColor(getColorsFromResource(itemView.context, R.color.blue_light))
                sideView.setBackgroundColor(getColorsFromResource(itemView.context, R.color.blue))
                tvTitle.setTextColor(getColorsFromResource(itemView.context, R.color.blue))
                tvDescription.setTextColor(getColorsFromResource(itemView.context, R.color.blue_medium))
                ivClose.setColorFilter(getColorsFromResource(itemView.context, R.color.blue))
            }
        }
    }
}