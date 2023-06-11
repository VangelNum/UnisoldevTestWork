package com.example.unisoldevtestwork.core.common

sealed class Resource<T>(val message: String? = null) {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(message: String) : Resource<T>(message)
    class Loading<T> : Resource<T>()
}