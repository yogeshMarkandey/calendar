package com.example.calendar.presentation.screen.main.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.presentation.screen.main.CalendarAdapter
import com.example.calendar.presentation.screen.main.CalendarAdapter.OnItemListener
import com.example.calendar.R
import com.example.calendar.domain.models.CalendarDate
import com.example.calendar.domain.models.Task
import com.example.calendar.presentation.screen.main.TaskRVAdapter
import com.example.calendar.presentation.screen.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemListener, TaskRVAdapter.OnTaskCardClicked {

    private val viewModel: MainViewModel by viewModels()
    private val rvAdapter = CalendarAdapter( this)
    private var monthYearText: TextView? = null
    private var calendarRecyclerView: RecyclerView? = null
    private var selectedDate: LocalDate = LocalDate.now()
    private var dummyList = arrayListOf<CalendarDate>()
    private val taskRvAdapter: TaskRVAdapter = TaskRVAdapter(this)
    private var taskRecyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.updateSelectedDate(LocalDate.now())
        initWidgets()
        setupObservers()
        setMonthView()
        viewModel.getTasks()
    }

    private fun initWidgets() {
        dummyList = arrayListOf<CalendarDate>()
        for (v in 1..42) dummyList.add(CalendarDate("", date = "$v"))
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearTV)

        rvAdapter.submitList(dummyList)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView?.layoutManager = layoutManager
        calendarRecyclerView?.adapter = rvAdapter


        taskRecyclerView = findViewById(R.id.taskRecyclerView)
        taskRecyclerView?.adapter = taskRvAdapter
        taskRecyclerView?.layoutManager = LinearLayoutManager(this)

        val addTaskButton = findViewById<Button>(R.id.addTaskButton)

        addTaskButton.setOnClickListener {
            viewModel.addTasks()
        }

    }

    override fun onDeleteClicked(task: Task) {
        viewModel.deleteTasks(task)
    }

    private fun setupObservers(){
        viewModel.tasks.observe(this){
            if(it.isNotEmpty()){
                taskRvAdapter.submitList(it)
            }
        }
    }


    private fun setMonthView() {
        monthYearText?.text = viewModel.monthYearFromDate(selectedDate)
        val daysInMonth = viewModel.daysInMonthArray(selectedDate)
        rvAdapter.submitList(daysInMonth)
    }

    fun previousMonthAction(view: View?) {
        val newMonth = selectedDate.minusMonths(1)
        viewModel.updateSelectedDate(newMonth)
        selectedDate = newMonth
        setMonthView()
    }

    fun nextMonthAction(view: View?) {
        val newMonth = selectedDate.plusMonths(1)
        viewModel.updateSelectedDate(newMonth)
        selectedDate = newMonth
        setMonthView()
    }

    override fun onItemClick(cal: CalendarDate) {
        val message = cal.date
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        selectDate(cal)
    }

    private fun selectDate(cal: CalendarDate){
        CoroutineScope(Dispatchers.IO).launch{
            val list = arrayListOf<CalendarDate>()
            list.addAll(rvAdapter.getList())
            val prev = list.find { it.isSelected }
            if(prev != null){
                val pos = list.indexOf(prev)
                list[pos] = list[pos].copy(isSelected = false)
            }
            val pos = list.indexOf(cal)
            list[pos] = list[pos].copy(isSelected = true)

            withContext(Dispatchers.Main){
                rvAdapter.submitList(list)
            }
        }
    }
}