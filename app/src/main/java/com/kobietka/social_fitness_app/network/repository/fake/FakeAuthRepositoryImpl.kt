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
        when(Random.nextInt() % 10){
            1, 2, 3, 4, 5, 6 -> return Result.Success(data = registerUserResponse)
            7, 8 -> return Result.Failure(message = "Something went wrong. Try again later.")
            9, 0 -> return Result.Failure(message = "Cannot connect. Check your internet connection.")
        }
        return Result.Failure(message = "????")
    }

    override suspend fun loginUser(loginUserRequest: LoginUserRequest): Result<LoginUserResponse> {
        val loginUserResponse = LoginUserResponse(
            token = "token_value",
            refreshToken = "refresh_token_value"
        )
        delay(2000)
        when(Random.nextInt() % 10){
            1, 2, 3, 4, 5, 6 -> return Result.Success(data = loginUserResponse)
            7, 8 -> return Result.Failure(message = "Something went wrong. Try again later.")
            9, 0 -> return Result.Failure(message = "Cannot connect. Check your internet connection.")
        }
        return Result.Failure(message = "????")
    }
}