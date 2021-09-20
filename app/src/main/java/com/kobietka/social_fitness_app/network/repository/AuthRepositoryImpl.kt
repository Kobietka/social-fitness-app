package com.kobietka.social_fitness_app.network.repository

import com.kobietka.social_fitness_app.domain.repository.remote.AuthRepository
import com.kobietka.social_fitness_app.domain.service.AuthService
import com.kobietka.social_fitness_app.network.request.LoginUserRequest
import com.kobietka.social_fitness_app.network.request.RegisterUserRequest
import com.kobietka.social_fitness_app.network.response.LoginUserResponse
import com.kobietka.social_fitness_app.network.response.RegisterUserResponse
import com.kobietka.social_fitness_app.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class AuthRepositoryImpl
@Inject constructor(private val authService: AuthService): AuthRepository {
    override suspend fun registerUser(registerUserRequest: RegisterUserRequest): Result<RegisterUserResponse> {
        return try {
            val response = authService.registerUser(registerUserRequest = registerUserRequest)
            Result.Success(data = response)
        } catch (exception: IOException){
            Result.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            Result.Failure(message = "Something went wrong. Try again later.")
        }
    }

    override suspend fun loginUser(loginUserRequest: LoginUserRequest): Result<LoginUserResponse> {
        return try {
            val response = authService.loginUser(loginUserRequest = loginUserRequest)
            Result.Success(data = response)
        } catch (exception: IOException){
            Result.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            Result.Failure(message = "Something went wrong. Try again later.")
        }
    }
}