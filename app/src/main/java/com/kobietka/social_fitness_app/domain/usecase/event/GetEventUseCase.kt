package com.kobietka.social_fitness_app.domain.usecase.event

import com.kobietka.social_fitness_app.data.entity.toEvent
import com.kobietka.social_fitness_app.domain.model.Event
import com.kobietka.social_fitness_app.domain.repository.local.EventRepository
import kotlinx.coroutines.flow.first


class GetEventUseCase(private val eventRepository: EventRepository) {
    suspend operator fun invoke(eventId: String): Event {
        return eventRepository.getEventById(eventId = eventId).first().toEvent()
    }
}