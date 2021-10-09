package com.kobietka.social_fitness_app.network.request



data class UpdateUserDataRequest(
    val id: String,
    val email: String,
    val nickname: String,
    val password: String
)
