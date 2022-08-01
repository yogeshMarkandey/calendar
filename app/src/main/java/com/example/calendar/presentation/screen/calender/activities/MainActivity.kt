package com.example.calendar.presentation.screen.calender.activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.domain.models.CalendarDate
import com.example.calendar.domain.models.Task
import com.example.calendar.presentation.screen.calender.adapters.CalendarAdapter
import com.example.calendar.presentation.screen.calender.adapters.CalendarAdapter.OnItemListener
import com.example.calendar.presentation.screen.calender.adapters.TaskRVAdapter
import com.example.calendar.presentation.screen.calender.fragments.AddTaskBottomSheetFragment
import com.example.calendar.presentation.screen.calender.viewmodel.MainViewModel
import com.example.calendar.presentation.util.ViewHelper.checkConnection
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemListener, TaskRVAdapter.OnTaskCardClicked {

    private val viewModel: MainViewModel by viewModels()
    private val rvAdapter = CalendarAdapter(this)
    private var monthYearText: TextView? = null
    private var calendarRecyclerView: RecyclerView? = null
    private var selectedDate: LocalDate = LocalDate.now()
    private var dummyList = arrayListOf<CalendarDate>()
    private val taskRvAdapter: TaskRVAdapter = TaskRVAdapter(this)
    private var taskRecyclerView: RecyclerView? = null
    private var noTaskAddedView: ConstraintLayout? = null
    private lateinit var rootLayout: CoordinatorLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContentView(R.layout.activity_main)
        viewModel.updateSelectedMonth(LocalDate.now())
        initWidgets()
        setupObservers()
        setMonthView()
        viewModel.getTasks()
    }

    private fun initWidgets() {
        noTaskAddedView = findViewById(R.id.noTaskAvailable)
        rootLayout = findViewById(R.id.rootLayout)
        dummyList = arrayListOf<CalendarDate>()
        for (v in 1..42) dummyList.add(CalendarDate("", date = "$v"))
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearTV)

        viewModel.updateDatesToDisplay(dummyList)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView?.layoutManager = layoutManager
        calendarRecyclerView?.adapter = rvAdapter


        taskRecyclerView = findViewById(R.id.taskRecyclerView)
        taskRecyclerView?.adapter = taskRvAdapter
        taskRecyclerView?.layoutManager = LinearLayoutManager(this)

        val addTaskButton = findViewById<FloatingActionButton>(R.id.addTaskButton)

        addTaskButton.setOnClickListener {
            val bottomSheet = AddTaskBottomSheetFragment(
                object : AddTaskBottomSheetFragment.OnAddClickListener {
                    override fun onClick(title: String, description: String, tags: String) {
                        if (checkConnection(this@MainActivity)) {
                            viewModel.addTasks(title, description, tags)
                        } else {
                            Snackbar.make(
                                this@MainActivity,
                                rootLayout,
                                "No Internet. Please Try again later.",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            )

            bottomSheet.isCancelable = true
            bottomSheet.show(supportFragmentManager, "add_task_bottom_sheet")
        }

    }

    override fun onDeleteClicked(task: Task) {
        if (checkConnection(this)) {
            viewModel.deleteTasks(task)
        } else {
            Snackbar.make(
                this.rootLayout,
                "No Internet. Please try again later.",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupObservers() {
        viewModel.tasks.observe(this) {
            taskRvAdapter.submitList(it)
            noTaskAddedView?.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.datesToDisplay.observe(this) {
            rvAdapter.submitList(it)
        }
    }

    private fun setMonthView() {
        monthYearText?.text = viewModel.monthYearFromDate(selectedDate)
        val daysInMonth = viewModel.daysInMonthArray(selectedDate)
        viewModel.updateDatesToDisplay(daysInMonth)
        viewModel.getTotalTaskForTheDay(daysInMonth)
    }

    fun previousMonthAction(view: View?) {
        val newMonth = selectedDate.minusMonths(1)
        viewModel.updateSelectedMonth(newMonth)
        selectedDate = newMonth
        setMonthView()
    }

    fun nextMonthAction(view: View?) {
        val newMonth = selectedDate.plusMonths(1)
        viewModel.updateSelectedMonth(newMonth)
        selectedDate = newMonth
        setMonthView()
    }

    override fun onItemClick(cal: CalendarDate) {
        viewModel.selectDate(cal)
    }
}