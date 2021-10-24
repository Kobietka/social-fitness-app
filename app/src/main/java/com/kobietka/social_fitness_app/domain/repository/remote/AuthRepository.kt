package com.kobietka.social_fitness_app.domain.repository.remote

import com.kobietka.social_fitness_app.network.request.LoginUserRequest
import com.kobietka.social_fitness_app.network.request.RegisterUserRequest
import com.kobietka.social_fitness_app.network.response.LoginUserResponse
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Result


interface AuthRepository {
    suspend fun registerUser(registerUserRequest: RegisterUserRequest): NetworkResult<Boolean>
    suspend fun loginUser(loginUserRequest: LoginUserRequest): NetworkResult<LoginUserResponse>
}