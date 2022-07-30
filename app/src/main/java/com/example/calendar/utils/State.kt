package com.example.calendar.utils

sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Error(val error: StateError) : State<Nothing>()
    data class Success<T>(var data: T) : State<T>()
}