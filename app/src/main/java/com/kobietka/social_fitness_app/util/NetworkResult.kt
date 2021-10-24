package com.kobietka.social_fitness_app.util



sealed class NetworkResult<T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure<T>(val message: String) : NetworkResult<T>()
    class Unauthorized<T> : NetworkResult<T>()
}
