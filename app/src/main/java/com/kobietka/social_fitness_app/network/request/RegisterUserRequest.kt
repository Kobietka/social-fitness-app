package com.kobietka.social_fitness_app.network.request


data class RegisterUserRequest(
    val nickname: String,
    val email: String,
    val password: String
)
