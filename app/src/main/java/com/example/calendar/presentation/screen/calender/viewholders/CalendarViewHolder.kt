package com.example.calendar.presentation.screen.calender.viewholders

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.domain.models.CalendarDate
import com.example.calendar.presentation.screen.calender.adapters.CalendarAdapter


class CalendarViewHolder(
    private val itemView: View,
    onItemListener: CalendarAdapter.OnItemListener
) : RecyclerView.ViewHolder(itemView) {

    val dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)
    private val itemListener: CalendarAdapter.OnItemListener = onItemListener
    val rootLayout: ConstraintLayout = itemView.findViewById(R.id.calendar_cell_root)
    private val cellBackgroundView: View = itemView.findViewById(R.id.cell_background)

    fun bind(cal: CalendarDate) {
        dayOfMonth.text = cal.day

        if (cal.isSelected) enableBackground()
        else disableBackground()

        itemView.setOnClickListener {
            itemListener.onItemClick(cal)
        }
    }

    private fun enableBackground() {
        cellBackgroundView.visibility = View.VISIBLE
    }

    private fun disableBackground() {
        cellBackgroundView.visibility = View.GONE
    }
}