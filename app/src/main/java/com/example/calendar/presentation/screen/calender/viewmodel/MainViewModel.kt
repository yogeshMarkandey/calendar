package com.example.calendar.presentation.screen.calender.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.data.models.db.task.toTask
import com.example.calendar.data.models.remote.task.add.AddTaskRequest
import com.example.calendar.data.models.remote.task.delete.DeleteTaskRequest
import com.example.calendar.data.models.remote.task.fetch.FetchTasksRequest
import com.example.calendar.domain.models.CalendarDate
import com.example.calendar.domain.models.Task
import com.example.calendar.domain.repositories.TasksRepository
import com.example.calendar.presentation.util.DateTimeHelper
import com.example.calendar.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
) : ViewModel() {
    private val userId: Int = 7024

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    private val _task = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _task

    private val todaysDate: LocalDate = LocalDate.now()
    private var selectedMonth: LocalDate = LocalDate.now()

    private val _datesToDisplay = MutableLiveData<List<CalendarDate>>(emptyList())
    val datesToDisplay: LiveData<List<CalendarDate>> get() = _datesToDisplay

    private var selectedDate = LocalDate.now()

    init {
        getAllTasks()
    }

    fun daysInMonthArray(date: LocalDate): ArrayList<CalendarDate> {
        val daysInMonthArray = ArrayList<CalendarDate>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedMonth.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        val lastDayOfMouth = date.withDayOfMonth(date.month.length(date.isLeapYear))

        val selectedDateFormatted = dateTimeFormatter.format(selectedDate)
        for (i in 1..42) {
            when {
                i <= dayOfWeek -> {
                    val d = firstOfMonth?.plusDays(-(dayOfWeek + 1 - i.toLong()))
                    val formattedDate = dateTimeFormatter.format(d)
                    daysInMonthArray.add(
                        CalendarDate(
                            day = "${d?.dayOfMonth}",
                            date = formattedDate,
                            isSelected = selectedDateFormatted == formattedDate,
                            isPartOfCurrentMonth = false
                        )
                    )
                }
                i > daysInMonth + dayOfWeek -> {
                    val d = lastDayOfMouth.plusDays((i - daysInMonth - dayOfWeek).toLong())
                    val formattedDate = dateTimeFormatter.format(d)
                    daysInMonthArray.add(
                        CalendarDate(
                            day = "${d?.dayOfMonth}",
                            date = formattedDate,
                            isSelected = selectedDateFormatted == formattedDate,
                            isPartOfCurrentMonth = false
                        )
                    )
                }
                else -> {
                    val d = firstOfMonth.plusDays((i - dayOfWeek - 1).toLong())
                    val formattedDate = dateTimeFormatter.format(d)

                    val day = i - dayOfWeek
                    daysInMonthArray.add(
                        CalendarDate(
                            day = "${d?.dayOfMonth}",// day.toString(),
                            isSelected = selectedDateFormatted == formattedDate,
                            date = formattedDate,
                            isPartOfCurrentMonth = true
                        )
                    )
                }
            }
        }
        return daysInMonthArray
    }

    private var allTasks = emptyList<Task>()
    private fun getAllTasks(){
        viewModelScope.launch {
            tasksRepository.getAllTasks().collect{
                val list = it.map { t -> t.toTask() }
                allTasks = list
                updateTasksList()
            }
        }
    }

    fun updateDatesToDisplay(list: List<CalendarDate>) {
        _datesToDisplay.postValue(list)
    }

    fun getTotalTaskForTheDay(list: List<CalendarDate>){
        viewModelScope.launch {
            val dates = ArrayList<CalendarDate>()
            val allTask = allTasks
            list.forEach { date ->
                val l = allTask.filter { it.dueDate ==  date.date}
                dates.add(date.copy(taskCount = l.size))
            }
            updateDatesToDisplay(dates)
        }
    }

    fun selectDate(cal: CalendarDate) {
        val currList = _datesToDisplay.value.orEmpty()
        val d = LocalDate.parse(cal.date, dateTimeFormatter)
        updatedSelectedDate(d)
        updateTasksList()

        CoroutineScope(Dispatchers.IO).launch {
            val list = arrayListOf<CalendarDate>()
            list.addAll(currList)
            val prev = list.find { it.isSelected }
            if (prev != null) {
                val pos = list.indexOf(prev)
                list[pos] = list[pos].copy(isSelected = false)
            }
            val pos = list.indexOf(cal)
            if(pos != -1){
                list[pos] = list[pos].copy(isSelected = true)
            }
            updateDatesToDisplay(list)
            getTotalTaskForTheDay(list)
        }
    }

    private fun updateTasksList(){
        val list = allTasks
        val date = dateTimeFormatter.format(selectedDate)
        val filteredList = list.filter { task -> task.dueDate == date }
        _task.postValue(filteredList)
    }
    fun monthYearFromDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date?.format(formatter) ?: ""
    }

    fun updateSelectedMonth(date: LocalDate) {
        selectedMonth = date
    }

    fun updatedSelectedDate(date: LocalDate) {
        selectedDate = date
    }

    fun getTasks() {
        val req = FetchTasksRequest(userId = userId)
        tasksRepository.fetchTask(req).onEach {
            when (it) {
                is State.Loading -> {}
                is State.Success -> {}
                is State.Error -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun addTasks(title: String, description: String, tag: String) {
        val req = AddTaskRequest(
            userId = userId, AddTaskRequest.TaskDetail(
                description = description,
                title = title,
                dueDate = dateTimeFormatter.format(selectedDate),
                creationTime = getCreationTimestamp(),
                tag = tag
            )
        )
        tasksRepository.addTask(req).onEach {
            when (it) {
                is State.Loading -> {}
                is State.Success -> {
                    getTasks()
                }
                is State.Error -> {}
            }
        }.launchIn(viewModelScope)
    }

    private fun getCreationTimestamp(): Long{
        return DateTimeHelper.getCurrentTimeStamp()
    }
    fun deleteTasks(task: Task) {
        val req = DeleteTaskRequest(userId = userId, taskId = task.id)
        tasksRepository.deleteTask(req).onEach {
            when (it) {
                is State.Loading -> {}
                is State.Success -> { getTasks() }
                is State.Error -> {}
            }
        }.launchIn(viewModelScope)
    }
}