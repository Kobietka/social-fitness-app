package com.kobietka.social_fitness_app.domain.repository.local

import com.kobietka.social_fitness_app.data.entity.ActivityEntity
import kotlinx.coroutines.flow.Flow


interface ActivityRepository {
    suspend fun insert(activityEntity: ActivityEntity)
    fun getActivitiesForEvent(eventId: String): Flow<List<ActivityEntity>>
    fun getActivitiesForUser(userId: String): Flow<List<ActivityEntity>>
    suspend fun deleteActivity(id: String)
    suspend fun getActivitiesForUserAndEvent(userId: String, eventId: String): List<ActivityEntity>
}