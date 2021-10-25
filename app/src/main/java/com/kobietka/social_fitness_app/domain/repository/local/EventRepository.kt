package com.kobietka.social_fitness_app.domain.repository.local

import com.kobietka.social_fitness_app.data.entity.EventEntity
import kotlinx.coroutines.flow.Flow


interface EventRepository {
    suspend fun insert(eventEntity: EventEntity)
    fun getEventById(eventId: String): Flow<EventEntity>
    suspend fun deleteEventById(eventId: String)
    fun getEventsByGroupId(groupId: String): Flow<List<EventEntity>>
}