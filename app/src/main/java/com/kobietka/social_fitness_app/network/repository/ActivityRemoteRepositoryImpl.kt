package com.kobietka.social_fitness_app.network.repository

import com.google.gson.Gson
import com.kobietka.social_fitness_app.domain.repository.remote.ActivityRemoteRepository
import com.kobietka.social_fitness_app.domain.service.ActivityService
import com.kobietka.social_fitness_app.network.request.CreateActivityRequest
import com.kobietka.social_fitness_app.network.response.ActivityDto
import com.kobietka.social_fitness_app.network.response.InvalidFieldErrorResponse
import com.kobietka.social_fitness_app.util.NetworkResult
import retrofit2.HttpException
import java.io.IOException


class ActivityRemoteRepositoryImpl(
    private val activityService: ActivityService
) : ActivityRemoteRepository {
    override suspend fun createActivity(
        createActivityRequest: CreateActivityRequest
    ): NetworkResult<ActivityDto> {
        return try {
            val response = activityService.createActivity(createActivityRequest = createActivityRequest)
            NetworkResult.Success(data = response)
        } catch (exception: IOException){
            NetworkResult.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> NetworkResult.Unauthorized()
                422 -> {
                    val responseString = exception.response()?.errorBody()?.string()
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

    override suspend fun deleteActivity(activityId: String): NetworkResult<Boolean> {
        return try {
            activityService.deleteActivity(activityId = activityId)
            NetworkResult.Success(data = true)
        } catch (exception: IOException){
            NetworkResult.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> NetworkResult.Unauthorized()
                404 -> NetworkResult.Failure(message = "Activity not found")
                else -> NetworkResult.Failure(message = "Something went wrong. Try again later.")
            }
        }
    }
}



















