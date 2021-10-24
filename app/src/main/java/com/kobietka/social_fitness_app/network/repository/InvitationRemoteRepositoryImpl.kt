package com.kobietka.social_fitness_app.network.repository

import com.google.gson.Gson
import com.kobietka.social_fitness_app.domain.repository.remote.InvitationRemoteRepository
import com.kobietka.social_fitness_app.domain.service.InvitationService
import com.kobietka.social_fitness_app.network.request.CreateInvitationRequest
import com.kobietka.social_fitness_app.network.response.InvalidFieldErrorResponse
import com.kobietka.social_fitness_app.network.response.InvitationResponse
import com.kobietka.social_fitness_app.util.NetworkResult
import retrofit2.HttpException
import java.io.IOException


class InvitationRemoteRepositoryImpl(
    private val invitationService: InvitationService
) : InvitationRemoteRepository {
    override suspend fun createInvitation(
        createInvitationRequest: CreateInvitationRequest
    ): NetworkResult<InvitationResponse> {
        return try {
            val response = invitationService.createInvitation(createInvitationRequest = createInvitationRequest)
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

    override suspend fun deleteInvitation(id: String): NetworkResult<Boolean> {
        return try {
            invitationService.deleteInvitation(id = id)
            NetworkResult.Success(data = true)
        } catch (exception: IOException){
            NetworkResult.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> NetworkResult.Unauthorized()
                404 -> NetworkResult.Failure(message = "Not found")
                else -> NetworkResult.Failure(message = "Something went wrong. Try again later.")
            }
        }
    }
}




















