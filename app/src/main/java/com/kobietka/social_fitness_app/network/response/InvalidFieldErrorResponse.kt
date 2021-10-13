package com.kobietka.social_fitness_app.network.response


data class InvalidFieldErrorResponse(
    val violations: List<Violation>
)
