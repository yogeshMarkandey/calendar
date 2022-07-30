package com.example.calendar
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CalendarViewHolder(itemView: View, onItemListener: CalendarAdapter.OnItemListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)
    private val itemListener: CalendarAdapter.OnItemListener = onItemListener
    override fun onClick(view: View) {
        itemListener.onItemClick(adapterPosition, dayOfMonth.text as String)
    }

    init {
        itemView.setOnClickListener(this)
    }
}