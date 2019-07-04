package io.github.pps5.kakaosampleapp.vo

sealed class Result<T> {
    data class Success<T>(val value: T): Result<T>()
    data class Failure<T>(val throwable: Throwable): Result<T>()
    object Loading: Result<Any>()
}