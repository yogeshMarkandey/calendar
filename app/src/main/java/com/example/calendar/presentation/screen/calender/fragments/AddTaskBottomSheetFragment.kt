package com.example.calendar.presentation.screen.calender.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.calendar.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddTaskBottomSheetFragment(
    private val onAddClickedListener: OnAddClickListener
) : BottomSheetDialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contentView = View.inflate(context, R.layout.add_task_bottomsheet, null)

        val button = contentView.findViewById<Button>(R.id.addTaskButton)
        val evTitle = contentView.findViewById<EditText>(R.id.etTitle)
        val evDescription = contentView.findViewById<EditText>(R.id.etDescription)
        button.setOnClickListener {
            onAddClickedListener.onClick(evTitle?.text.toString(), evDescription?.text.toString())
            dialog?.cancel()
        }
        return contentView
    }

    interface OnAddClickListener {
        fun onClick(title: String, description: String)
    }
}