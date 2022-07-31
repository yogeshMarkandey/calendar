package com.example.calendar.presentation.util

object DateTimeHelper {
    fun getCurrentTimeStamp(): Long {
        return System.currentTimeMillis() / 1000
    }
}