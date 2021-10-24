package com.kobietka.social_fitness_app.util

sealed class Progress {
    object Loading : Progress()
    object Finished : Progress()
    data class Error(val message: String) : Progress()
    object Unauthorized : Progress()
}
