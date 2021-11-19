package com.kobietka.social_fitness_app.domain.repository.local

import com.kobietka.social_fitness_app.data.entity.EventMemberEntity
import kotlinx.coroutines.flow.Flow

interface EventMemberRepository {
    suspend fun insert(eventMemberEntity: EventMemberEntity)
    fun getEventMembers(eventId: String): Flow<List<EventMemberEntity>>
}