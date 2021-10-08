package com.kobietka.social_fitness_app.network.repository

import com.google.gson.Gson
import com.kobietka.social_fitness_app.domain.repository.remote.AuthRepository
import com.kobietka.social_fitness_app.domain.service.AuthService
import com.kobietka.social_fitness_app.network.request.LoginUserRequest
import com.kobietka.social_fitness_app.network.request.RegisterUserRequest
import com.kobietka.social_fitness_app.network.response.ErrorResponse
import com.kobietka.social_fitness_app.network.response.LoginUserResponse
import com.kobietka.social_fitness_app.network.response.RegisterUserErrorResponse
import com.kobietka.social_fitness_app.util.Result
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class AuthRepositoryImpl
@Inject constructor(private val authService: AuthService): AuthRepository {
    override suspend fun registerUser(registerUserRequest: RegisterUserRequest): Result<Boolean> {
        return try {
            authService.registerUser(registerUserRequest = registerUserRequest)
            Result.Success(data = true)
        } catch (exception: IOException){
            Result.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            val responseString = exception.response()?.errorBody()?.string()
            return when(exception.code()){
                422 -> {
                    val errorResponse = Gson().fromJson(responseString, RegisterUserErrorResponse::class.java)
                    val message = errorResponse.violations.foldRight(initial = ""){ violation, acc ->
                        acc + violation.message + "\n"
                    }
                    Result.Failure(
                        message = message
                    )
                }
                else -> {
                    Result.Failure(message = "Something went wrong. Try again later.")
                }
            }
        }
    }

    override suspend fun loginUser(loginUserRequest: LoginUserRequest): Result<LoginUserResponse> {
        return try {
            val response = authService.loginUser(loginUserRequest = loginUserRequest)
            Result.Success(data = response)
        } catch (exception: IOException){
            Result.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            val responseString = exception.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(responseString, ErrorResponse::class.java)
            Result.Failure(message = errorResponse.message)
        }
    }
}


















