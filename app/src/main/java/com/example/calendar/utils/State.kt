package com.example.calendar.utils

sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Error(var exception: Throwable) : State<Nothing>()
    data class Success<T>(var data: T) : State<T>()
}