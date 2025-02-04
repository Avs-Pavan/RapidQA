package com.pavan.rapidqa.sample.domain

sealed interface Result<T, E> {
    data class Success<T, E>(val data: T) : Result<T, E>
    data class Error<T, E>(val error: E) : Result<T, E>
}