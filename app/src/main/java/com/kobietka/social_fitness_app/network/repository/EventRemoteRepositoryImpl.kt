package com.kobietka.social_fitness_app.network.repository

import com.google.gson.Gson
import com.kobietka.social_fitness_app.domain.repository.remote.EventRemoteRepository
import com.kobietka.social_fitness_app.domain.service.EventService
import com.kobietka.social_fitness_app.network.request.CreateEventRequest
import com.kobietka.social_fitness_app.network.request.EditEventRequest
import com.kobietka.social_fitness_app.network.response.EventDto
import com.kobietka.social_fitness_app.network.response.InvalidFieldErrorResponse
import com.kobietka.social_fitness_app.util.NetworkResult
import retrofit2.HttpException
import java.io.IOException


class EventRemoteRepositoryImpl(private val eventService: EventService) : EventRemoteRepository {
    override suspend fun createEvent(createEventRequest: CreateEventRequest): NetworkResult<EventDto> {
        return try {
            val response = eventService.createEvent(createEventRequest = createEventRequest)
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

    override suspend fun getEvent(eventId: String): NetworkResult<EventDto> {
        return try {
            val response = eventService.getEvent(eventId = eventId)
            NetworkResult.Success(data = response)
        } catch (exception: IOException){
            NetworkResult.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> NetworkResult.Unauthorized()
                404 -> NetworkResult.Failure(message = "Event not found")
                else -> NetworkResult.Failure(message = "Something went wrong. Try again later.")
            }
        }
    }

    override suspend fun editEvent(
        eventId: String,
        editEventRequest: EditEventRequest
    ): NetworkResult<EventDto> {
        return try {
            val response = eventService.editEvent(
                eventId = eventId,
                editEventRequest = editEventRequest
            )
            NetworkResult.Success(data = response)
        } catch (exception: IOException){
            NetworkResult.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> NetworkResult.Unauthorized()
                404 -> NetworkResult.Failure(message = "Event not found")
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

    override suspend fun deleteEvent(eventId: String): NetworkResult<Boolean> {
        return try {
            eventService.deleteEvent(eventId = eventId)
            NetworkResult.Success(data = true)
        } catch (exception: IOException){
            NetworkResult.Failure(message = "Cannot connect. Check your internet connection.")
        } catch (exception: HttpException){
            return when(exception.code()){
                401 -> NetworkResult.Unauthorized()
                404 -> NetworkResult.Failure(message = "Event not found")
                else -> NetworkResult.Failure(message = "Something went wrong. Try again later.")
            }
        }
    }
}





















