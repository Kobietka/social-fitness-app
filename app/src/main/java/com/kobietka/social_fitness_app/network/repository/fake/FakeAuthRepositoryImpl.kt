package com.kobietka.social_fitness_app.network.repository.fake

import com.kobietka.social_fitness_app.domain.repository.remote.AuthRepository
import com.kobietka.social_fitness_app.network.request.LoginUserRequest
import com.kobietka.social_fitness_app.network.request.RegisterUserRequest
import com.kobietka.social_fitness_app.network.response.LoginUserResponse
import com.kobietka.social_fitness_app.network.response.RegisterUserResponse
import com.kobietka.social_fitness_app.util.Result
import kotlinx.coroutines.delay
import kotlin.random.Random


class FakeAuthRepositoryImpl : AuthRepository {
    override suspend fun registerUser(registerUserRequest: RegisterUserRequest): Result<RegisterUserResponse> {
        val registerUserResponse = RegisterUserResponse(
            userId = 1,
            name = registerUserRequest.nickname,
            email = registerUserRequest.email
        )
        delay(2000)
        when(Random.nextInt() % 3){
            0 -> return Result.Success(data = registerUserResponse)
            1 -> return Result.Failure(message = "Something went wrong. Try again later.")
            2 -> return Result.Failure(message = "Cannot connect. Check your internet connection.")
        }
        return Result.Failure(message = "????")
    }

    override suspend fun loginUser(loginUserRequest: LoginUserRequest): Result<LoginUserResponse> {
        TODO("Not yet implemented")
    }
}