package com.example.calendar.presentation.screen.main.activities

import android.os.Bundle
import android.view.View
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
class MainActivity : AppCompatActivity(), OnItemListener {

    private val viewModel: MainViewModel by viewModels()
    private val rvAdapter = CalendarAdapter( this)
    private var monthYearText: TextView? = null
    private var calendarRecyclerView: RecyclerView? = null
    private var todaysDate : LocalDate = LocalDate.now()
    private var selectedDate: LocalDate = LocalDate.now()
    private var dummyList = arrayListOf<CalendarDate>()
    private var dummyTasks = listOf(
        Task(
            id = 0,
            title = "Title 1",
            description = "Des 1"
        ),
        Task(
            id = 2,
            title = "Title 2",
            description = "Des 1"
        ),
        Task(
            id = 3,
            title = "Title 3",
            description = "Des 1"
        )
    )
    private val taskRvAdapter: TaskRVAdapter = TaskRVAdapter()
    private var taskRecyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWidgets()
        setMonthView()
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
        taskRvAdapter.submitList(dummyTasks)

    }


    private fun setMonthView() {
        monthYearText?.text = monthYearFromDate(selectedDate)
        val daysInMonth = daysInMonthArray(selectedDate)
        rvAdapter.submitList(daysInMonth)
    }

    private fun daysInMonthArray(date: LocalDate): ArrayList<CalendarDate> {
        val daysInMonthArray = ArrayList<CalendarDate>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        val lastDayOfMouth = date.withDayOfMonth(date.month.length(date.isLeapYear))

        val formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val todaysFormattedDate = formatter.format(todaysDate)
        for (i in 1..42) {
            when {
                i <= dayOfWeek -> {
                    val d = firstOfMonth?.plusDays(-(dayOfWeek + 1 -i.toLong()))
                    val p = formatter.format(d)
                    daysInMonthArray.add(CalendarDate(day = "${d?.dayOfMonth}", date = p, isSelected = todaysFormattedDate == p))
                }
                i > daysInMonth + dayOfWeek -> {
                    val d = lastDayOfMouth.plusDays((i - daysInMonth-dayOfWeek).toLong())
                    val p = formatter.format(d)
                    daysInMonthArray.add(CalendarDate(day = "${d?.dayOfMonth}", date = p, isSelected = todaysFormattedDate == p))
                }
                else -> {
                    val d = firstOfMonth.plusDays((i-dayOfWeek-1).toLong())
                    val p = formatter.format(d)

                    val day = i - dayOfWeek
                    daysInMonthArray.add(
                        CalendarDate(
                            day = "${d?.dayOfMonth}" ,// day.toString(),
                            isSelected = todaysFormattedDate == p,
                            date = p
                        )
                    )
                }
            }
        }
        return daysInMonthArray
    }

    private fun monthYearFromDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date?.format(formatter) ?: ""
    }

    fun previousMonthAction(view: View?) {
        selectedDate = selectedDate.minusMonths(1)
        setMonthView()
    }

    fun nextMonthAction(view: View?) {
        selectedDate = selectedDate.plusMonths(1)
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