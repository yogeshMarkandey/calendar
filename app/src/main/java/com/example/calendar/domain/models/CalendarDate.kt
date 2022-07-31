package com.example.calendar.domain.models

data class CalendarDate(
    val day: String,
    var isSelected: Boolean = false,
    val date: String
)
