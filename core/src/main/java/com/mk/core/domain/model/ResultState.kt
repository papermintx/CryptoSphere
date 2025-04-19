package com.mk.core.domain.model

sealed class ResultState<out T> {
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error(val message: String) : ResultState<Nothing>()
    object Idle : ResultState<Nothing>()
    object NothingData: ResultState<Nothing>()
    object Loading : ResultState<Nothing>()
}
