package com.kobietka.social_fitness_app.domain.usecase.event

import com.kobietka.social_fitness_app.data.entity.toActivity
import com.kobietka.social_fitness_app.domain.model.EventMember
import com.kobietka.social_fitness_app.domain.repository.local.ActivityRepository


class MatchEventMembersWithActivitiesUseCase(
    private val activityRepository: ActivityRepository
) {
    suspend operator fun invoke(
        eventId: String,
        eventMembers: List<EventMember>
    ): List<EventMember> {
        val matchedEventMembers = mutableListOf<EventMember>()

        eventMembers.forEach { eventMember ->
            val activities = activityRepository.getActivitiesForUserAndEvent(
                userId = eventMember.userId,
                eventId = eventId
            ).map { it.toActivity() }
            matchedEventMembers.add(eventMember.copy(activities = activities))
        }

        return matchedEventMembers
    }
}