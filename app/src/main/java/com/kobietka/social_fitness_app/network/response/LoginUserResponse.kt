package com.kobietka.social_fitness_app.network.response



data class LoginUserResponse(
    val token: String,
    val id: String,
    val nickname: String
)
