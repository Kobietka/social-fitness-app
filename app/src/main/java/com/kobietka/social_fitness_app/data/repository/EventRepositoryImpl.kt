package com.kobietka.social_fitness_app.data.repository

import com.kobietka.social_fitness_app.data.dao.EventDao
import com.kobietka.social_fitness_app.data.entity.EventEntity
import com.kobietka.social_fitness_app.domain.repository.local.EventRepository
import kotlinx.coroutines.flow.Flow


class EventRepositoryImpl(private val eventDao: EventDao) : EventRepository {
    override suspend fun insert(eventEntity: EventEntity) {
        eventDao.insert(eventEntity = eventEntity)
    }

    override fun getEventById(eventId: String): Flow<EventEntity> {
        return eventDao.getEventById(eventId = eventId)
    }

    override suspend fun deleteEventById(eventId: String) {
        eventDao.deleteEventById(eventId = eventId)
    }

    override fun getEventsByGroupId(groupId: String): Flow<List<EventEntity>> {
        return eventDao.getEventsByGroupId(groupId = groupId)
    }
}