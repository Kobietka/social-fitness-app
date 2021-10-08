package com.kobietka.social_fitness_app.network.response



data class Violation (
    val propertyPath: String,
    val message: String,
    val code: Int?
)
