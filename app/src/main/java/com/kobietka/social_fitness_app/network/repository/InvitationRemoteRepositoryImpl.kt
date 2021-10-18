package com.kobietka.social_fitness_app.network.repository

import com.google.gson.Gson
import com.kobietka.social_fitness_app.domain.repository.remote.InvitationRemoteRepository
import com.kobietka.social_fitness_app.domain.service.InvitationService
import com.kobietka.social_fitness_app.network.request.CreateInvitationRequest
import com.kobietka.social_fitness_app.network.request.EditInvitationRequest
import com.kobietka.social_fitness_app.network.response.InvalidFieldErrorResponse
import com.kobietka.social_fitness_app.network.response.InvitationResponse
import com.kobietka.social_fitness_app.util.Result
import retrofit2.HttpException
import java.io.IOException


class InvitationRemoteRepositoryImpl(
    private val invitationService: InvitationService
) : InvitationRemoteRepository {
    override suspend fun createInvitation(
        createInvitationRequest: CreateInvitationRequest
    ): Result<InvitationResponse> {
        return try {
            val response = invitationService.createInvitation(createInvitationRequest = createInvitationRequest)
            Result.Success(data = response)
        } catch (exception: IOException){
            Result.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> Result.Unauthorized()
                422 -> {
                    val responseString = exception.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(responseString, InvalidFieldErrorResponse::class.java)
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

    override suspend fun deleteInvitation(id: String): Result<Boolean> {
        return try {
            invitationService.deleteInvitation(id = id)
            Result.Success(data = true)
        } catch (exception: IOException){
            Result.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> Result.Unauthorized()
                404 -> Result.Failure(message = "Not found")
                else -> {
                    Result.Failure(message = "Something went wrong. Try again later.")
                }
            }
        }
    }

    override suspend fun editInvitation(
        id: String,
        editInvitationRequest: EditInvitationRequest
    ): Result<InvitationResponse> {
        return try {
            val response = invitationService.editInvitation(
                id = id,
                editInvitationRequest = editInvitationRequest
            )
            Result.Success(data = response)
        } catch (exception: IOException){
            Result.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> Result.Unauthorized()
                404 -> Result.Failure(message = "Not found")
                422 -> {
                    val responseString = exception.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(responseString, InvalidFieldErrorResponse::class.java)
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



















