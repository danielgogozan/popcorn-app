package com.neusoft.moviesapp.utils

sealed class ResultHandler<out R> {

    data class Success<out T>(val data: T) : ResultHandler<T>()

    data class Error(val exception: Exception) : ResultHandler<Nothing>()

    object Loading : ResultHandler<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success [data=$data]"
            is Error -> "Error [exception=$exception]"
            Loading -> "Loading"
        }
    }
}

val ResultHandler<*>.succeeded
    get() = this is ResultHandler.Success && data != null

val ResultHandler<*>.failed
    get() = this is ResultHandler.Error

val ResultHandler<*>.loading
    get() = this is ResultHandler.Loading
