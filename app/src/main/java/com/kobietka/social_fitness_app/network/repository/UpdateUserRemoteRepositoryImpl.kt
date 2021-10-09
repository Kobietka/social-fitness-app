package com.kobietka.social_fitness_app.network.repository

import com.google.gson.Gson
import com.kobietka.social_fitness_app.domain.repository.remote.UpdateUserRemoteRepository
import com.kobietka.social_fitness_app.domain.service.UpdateUserService
import com.kobietka.social_fitness_app.network.request.UpdateUserDataRequest
import com.kobietka.social_fitness_app.network.request.UpdateUserPasswordRequest
import com.kobietka.social_fitness_app.network.response.RegisterUserErrorResponse
import com.kobietka.social_fitness_app.network.response.UpdateUserDataResponse
import com.kobietka.social_fitness_app.util.Result
import retrofit2.HttpException
import java.io.IOException


class UpdateUserRemoteRepositoryImpl(
    private val updateUserService: UpdateUserService
) : UpdateUserRemoteRepository {
    override suspend fun updateUserPassword(
        userId: String,
        updateUserPasswordRequest: UpdateUserPasswordRequest
    ): Result<Boolean> {
        return try {
            updateUserService.updateUserPassword(
                userId = userId,
                updateUserPasswordRequest = updateUserPasswordRequest
            )
            Result.Success(data = true)
        } catch (exception: IOException){
            Result.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            val responseString = exception.response()?.errorBody()?.string()
            return when(exception.code()){
                401 -> {
                    Result.Unauthorized()
                }
                422 -> {
                    val errorResponse = Gson().fromJson(responseString, RegisterUserErrorResponse::class.java)
                    val message = errorResponse.violations.foldRight(initial = ""){ violation, acc ->
                        acc + violation.message + "\n"
                    }
                    Result.Failure(message = message)
                }
                else -> {
                    Result.Failure(message = "Something went wrong. Try again later.")
                }
            }
        }
    }

    override suspend fun updateUserData(
        userId: String,
        updateUserDataRequest: UpdateUserDataRequest
    ): Result<UpdateUserDataResponse> {
        return try {
            val response = updateUserService.updateUserData(
                userId = userId,
                updateUserDataRequest = updateUserDataRequest
            )
            Result.Success(data = response)
        } catch (exception: IOException){
            Result.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            val responseString = exception.response()?.errorBody()?.string()
            return when(exception.code()){
                401 -> {
                    Result.Unauthorized()
                }
                422 -> {
                    val errorResponse = Gson().fromJson(responseString, RegisterUserErrorResponse::class.java)
                    val message = errorResponse.violations.foldRight(initial = ""){ violation, acc ->
                        acc + violation.message + "\n"
                    }
                    Result.Failure(message = message)
                }
                else -> {
                    Result.Failure(message = "Something went wrong. Try again later.")
                }
            }
        }
    }
}



















