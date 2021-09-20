package com.kobietka.social_fitness_app.domain.repository.remote

import com.kobietka.social_fitness_app.network.request.LoginUserRequest
import com.kobietka.social_fitness_app.network.request.RegisterUserRequest
import com.kobietka.social_fitness_app.network.response.LoginUserResponse
import com.kobietka.social_fitness_app.network.response.RegisterUserResponse
import com.kobietka.social_fitness_app.util.Result
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    suspend fun registerUser(registerUserRequest: RegisterUserRequest): Result<RegisterUserResponse>
    suspend fun loginUser(loginUserRequest: LoginUserRequest): Result<LoginUserResponse>
}