package com.kobietka.social_fitness_app.domain.service

import com.kobietka.social_fitness_app.network.request.LoginUserRequest
import com.kobietka.social_fitness_app.network.request.RegisterUserRequest
import com.kobietka.social_fitness_app.network.response.LoginUserResponse
import com.kobietka.social_fitness_app.network.response.RegisterUserResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthService {

    @POST("/api/register")
    suspend fun registerUser(@Body registerUserRequest: RegisterUserRequest): RegisterUserResponse

    @POST("/api/login")
    suspend fun loginUser(@Body loginUserRequest: LoginUserRequest): LoginUserResponse

}