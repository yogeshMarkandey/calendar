package com.example.calendar.presentation.screen.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.data.models.task.add.AddTaskRequest
import com.example.calendar.data.models.task.delete.DeleteTaskRequest
import com.example.calendar.data.models.task.fetch.FetchTasksRequest
import com.example.calendar.domain.models.CalendarDate
import com.example.calendar.domain.models.Task
import com.example.calendar.domain.repositories.TasksRepository
import com.example.calendar.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
) : ViewModel() {
    private val userId: Int = 7009

    private val _task = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _task

    private val todaysDate : LocalDate = LocalDate.now()
    private var selectedDate: LocalDate = LocalDate.now()

    fun daysInMonthArray(date: LocalDate): ArrayList<CalendarDate> {
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

    fun monthYearFromDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date?.format(formatter) ?: ""
    }


    fun updateSelectedDate(date:LocalDate){
        selectedDate = date
    }

    fun getTasks() {
        val req = FetchTasksRequest(userId = userId)
        tasksRepository.fetchTask(req).onEach {
            when (it) {
                is State.Loading -> {

                }
                is State.Success -> {
                    _task.postValue(it.data?.ifEmpty { emptyList() })

                }
                is State.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    fun addTasks() {
        val random = Random()
        val req = AddTaskRequest(
            userId = userId, AddTaskRequest.TaskDetail(
                description = "Description : ${random.nextInt()}",
                title = "Title: ${random.nextInt()}",
                dueDate = "2022-04-22"
            )
        )
        tasksRepository.addTask(req).onEach {
            when (it) {
                is State.Loading -> {

                }
                is State.Success -> {
                    getTasks()
                }
                is State.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteTasks(task: Task) {
        val req = DeleteTaskRequest(userId = userId, taskId = task.id)
        tasksRepository.deleteTask(req).onEach {
            when (it) {
                is State.Loading -> {

                }
                is State.Success -> {
                    getTasks()
                }
                is State.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }
}