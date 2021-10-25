package com.kobietka.social_fitness_app.domain.repository.remote

import com.kobietka.social_fitness_app.network.request.CreateEventRequest
import com.kobietka.social_fitness_app.network.request.EditEventRequest
import com.kobietka.social_fitness_app.network.response.EventDto
import com.kobietka.social_fitness_app.util.NetworkResult


interface EventRemoteRepository {
    suspend fun createEvent(createEventRequest: CreateEventRequest): NetworkResult<EventDto>
    suspend fun getEvent(eventId: String): NetworkResult<EventDto>
    suspend fun editEvent(
        eventId: String,
        editEventRequest: EditEventRequest
    ): NetworkResult<EventDto>
    suspend fun deleteEvent(eventId: String): NetworkResult<Boolean>
}