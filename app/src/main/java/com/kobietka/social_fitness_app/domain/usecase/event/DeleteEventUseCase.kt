package com.kobietka.social_fitness_app.domain.usecase.event

import com.kobietka.social_fitness_app.domain.repository.local.EventRepository
import com.kobietka.social_fitness_app.domain.repository.remote.EventRemoteRepository
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class DeleteEventUseCase(
    private val eventRemoteRepository: EventRemoteRepository,
    private val eventRepository: EventRepository
) {
    operator fun invoke(eventId: String): Flow<Progress> = flow {
        emit(Progress.Loading)
        when(val result = eventRemoteRepository.deleteEvent(eventId = eventId)){
            is NetworkResult.Success -> {
                eventRepository.deleteEventById(eventId = eventId)
                emit(Progress.Finished)
            }
            is NetworkResult.Failure -> emit(Progress.Error(message = result.message))
            is NetworkResult.Unauthorized -> emit(Progress.Unauthorized)
        }
    }
}