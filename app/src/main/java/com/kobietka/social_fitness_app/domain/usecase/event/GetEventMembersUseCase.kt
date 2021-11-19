package com.kobietka.social_fitness_app.domain.usecase.event

import com.kobietka.social_fitness_app.data.entity.toEventMember
import com.kobietka.social_fitness_app.domain.model.EventMember
import com.kobietka.social_fitness_app.domain.repository.local.EventMemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetEventMembersUseCase(private val eventMemberRepository: EventMemberRepository) {
    operator fun invoke(eventId: String): Flow<List<EventMember>> {
        return eventMemberRepository.getEventMembers(eventId = eventId).map { eventMemberEntities ->
            eventMemberEntities.map { it.toEventMember() }
        }
    }
}