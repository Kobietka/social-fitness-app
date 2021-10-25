package com.kobietka.social_fitness_app.domain.service

import com.kobietka.social_fitness_app.network.request.CreateEventRequest
import com.kobietka.social_fitness_app.network.request.EditEventRequest
import com.kobietka.social_fitness_app.network.response.EventDto
import retrofit2.http.*


interface EventService {

    @POST("/api/event")
    suspend fun createEvent(@Body createEventRequest: CreateEventRequest): EventDto

    @GET("/api/event/{id}")
    suspend fun getEvent(@Path("id") eventId: String): EventDto

    @PATCH("/api/event/{id}")
    suspend fun editEvent(
        @Path("id") eventId: String,
        @Body editEventRequest: EditEventRequest
    ): EventDto

    @DELETE("/api/event/{id}")
    suspend fun deleteEvent(@Path("id") eventId: String)

}
























