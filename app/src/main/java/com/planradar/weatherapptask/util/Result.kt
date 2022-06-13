package com.planradar.weatherapptask.util

sealed class Result <out T : Any, out U : Any> {
    data class Success<T: Any>(val data : T?) : Result<T, Nothing>()
    data class Error <U : Any>(val message: U) : Result<Nothing, U>()
}
