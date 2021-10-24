package com.kobietka.social_fitness_app.network.repository

import com.google.gson.Gson
import com.kobietka.social_fitness_app.domain.repository.remote.UpdateUserRemoteRepository
import com.kobietka.social_fitness_app.domain.service.UpdateUserService
import com.kobietka.social_fitness_app.network.request.UpdateUserDataRequest
import com.kobietka.social_fitness_app.network.request.UpdateUserPasswordRequest
import com.kobietka.social_fitness_app.network.response.InvalidFieldErrorResponse
import com.kobietka.social_fitness_app.network.response.UpdateUserDataResponse
import com.kobietka.social_fitness_app.util.NetworkResult
import retrofit2.HttpException
import java.io.IOException


class UpdateUserRemoteRepositoryImpl(
    private val updateUserService: UpdateUserService
) : UpdateUserRemoteRepository {
    override suspend fun updateUserPassword(
        userId: String,
        updateUserPasswordRequest: UpdateUserPasswordRequest
    ): NetworkResult<Boolean> {
        return try {
            updateUserService.updateUserPassword(
                userId = userId,
                updateUserPasswordRequest = updateUserPasswordRequest
            )
            NetworkResult.Success(data = true)
        } catch (exception: IOException){
            NetworkResult.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            val responseString = exception.response()?.errorBody()?.string()
            return when(exception.code()){
                401 -> NetworkResult.Unauthorized()
                422 -> {
                    val errorResponse = Gson().fromJson(responseString, InvalidFieldErrorResponse::class.java)
                    val message = errorResponse.violations.foldRight(initial = ""){ violation, acc ->
                        acc + violation.message + "\n"
                    }
                    NetworkResult.Failure(message = message)
                }
                else -> NetworkResult.Failure(message = "Something went wrong. Try again later.")
            }
        }
    }

    override suspend fun updateUserData(
        userId: String,
        updateUserDataRequest: UpdateUserDataRequest
    ): NetworkResult<UpdateUserDataResponse> {
        return try {
            val response = updateUserService.updateUserData(
                userId = userId,
                updateUserDataRequest = updateUserDataRequest
            )
            NetworkResult.Success(data = response)
        } catch (exception: IOException){
            NetworkResult.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            val responseString = exception.response()?.errorBody()?.string()
            return when(exception.code()){
                401 -> NetworkResult.Unauthorized()
                422 -> {
                    val errorResponse = Gson().fromJson(responseString, InvalidFieldErrorResponse::class.java)
                    val message = errorResponse.violations.foldRight(initial = ""){ violation, acc ->
                        acc + violation.message + "\n"
                    }
                    NetworkResult.Failure(message = message)
                }
                else -> NetworkResult.Failure(message = "Something went wrong. Try again later.")
            }
        }
    }
}



















