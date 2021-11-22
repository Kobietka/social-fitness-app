package com.kobietka.social_fitness_app.domain.usecase.event

import com.kobietka.social_fitness_app.data.entity.toEvent
import com.kobietka.social_fitness_app.domain.model.Event
import com.kobietka.social_fitness_app.domain.repository.local.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetEventUseCase(private val eventRepository: EventRepository) {
    operator fun invoke(eventId: String): Flow<Event> {
        return eventRepository.getEventById(eventId = eventId).map { it.toEvent() }
    }
}