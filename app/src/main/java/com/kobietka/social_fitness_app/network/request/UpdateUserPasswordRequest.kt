package com.kobietka.social_fitness_app.network.request



data class UpdateUserPasswordRequest(
    val id: String,
    val password: String,
    val oldPassword: String
)
