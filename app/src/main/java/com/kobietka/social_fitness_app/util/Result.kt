package com.kobietka.social_fitness_app.util


sealed class Result<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T): Result<T>(data = data)
    class Failure<T>(message: String): Result<T>(message = message)
    class Unauthorized<T> : Result<T>()
}
