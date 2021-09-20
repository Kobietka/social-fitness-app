package com.kobietka.social_fitness_app.network.response

import com.google.gson.annotations.SerializedName


data class LoginUserResponse(
    val token: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
