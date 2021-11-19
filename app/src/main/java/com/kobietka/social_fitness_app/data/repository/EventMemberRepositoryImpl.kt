package com.kobietka.social_fitness_app.data.repository

import com.kobietka.social_fitness_app.data.dao.EventMemberDao
import com.kobietka.social_fitness_app.data.entity.EventMemberEntity
import com.kobietka.social_fitness_app.domain.repository.local.EventMemberRepository
import kotlinx.coroutines.flow.Flow


class EventMemberRepositoryImpl(
    private val eventMemberDao: EventMemberDao
) : EventMemberRepository {
    override suspend fun insert(eventMemberEntity: EventMemberEntity) {
        eventMemberDao.insert(eventMemberEntity = eventMemberEntity)
    }

    override fun getEventMembers(eventId: String): Flow<List<EventMemberEntity>> {
        return eventMemberDao.getEventMembers(eventId = eventId)
    }
}