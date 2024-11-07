package com.pavan.rapidqa.presentation

sealed interface UIState<T> {
    data object Loading : UIState<Nothing>
    data class Success<T>(val data: T) : UIState<T>
    data class Error(val message: String) : UIState<Nothing>
}