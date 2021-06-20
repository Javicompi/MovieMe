package es.jnsoft.movieme.data

sealed class Result<out T> {

    data class Success<out T>(val value: T) : Result<T>()
    data class Failure(val code: Int? = null, val message: String? = null) : Result<Nothing>()
}

val Result<*>.succeeded
    get() = this is Result.Success

val Result<*>.failed
    get() = this is Result.Failure
