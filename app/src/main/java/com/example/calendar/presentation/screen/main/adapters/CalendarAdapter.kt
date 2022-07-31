package com.example.calendar.presentation.screen.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.calendar.R
import com.example.calendar.domain.models.CalendarDate
import com.example.calendar.presentation.screen.main.viewholders.CalendarViewHolder


class CalendarAdapter(
    private val onItemListener: OnItemListener
) : ListAdapter<CalendarDate, CalendarViewHolder>(callback) {

    companion object{
        val callback = object : DiffUtil.ItemCallback<CalendarDate>(){
            override fun areItemsTheSame(oldItem: CalendarDate, newItem: CalendarDate): Boolean {
                return oldItem.day == newItem.day &&
                        oldItem.date == newItem.date &&
                        oldItem.isSelected == newItem.isSelected
            }

            override fun areContentsTheSame(oldItem: CalendarDate, newItem: CalendarDate): Boolean {
                return oldItem.isSelected == newItem.isSelected &&
                        oldItem.day == newItem.day &&
                        oldItem.date == newItem.date
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.calender_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.width * 0.1428571).toInt()
        return CalendarViewHolder(view, onItemListener)
    }

    fun getList() : List<CalendarDate> = currentList

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = getList()[position]
        holder.bind(date)
    }

    override fun getItemCount(): Int {
        return getList().size
    }

    interface OnItemListener {
        fun onItemClick(cal: CalendarDate)
    }
}