package com.example.calendar.presentation.screen.main.viewmodel

import androidx.lifecycle.ViewModel
import com.example.calendar.domain.repositories.TasksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tasksRepository: TasksRepository
) : ViewModel() {

}