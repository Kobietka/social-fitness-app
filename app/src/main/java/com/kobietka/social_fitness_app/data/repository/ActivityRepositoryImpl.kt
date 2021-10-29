package com.kobietka.social_fitness_app.data.repository

import com.kobietka.social_fitness_app.data.dao.ActivityDao
import com.kobietka.social_fitness_app.data.entity.ActivityEntity
import com.kobietka.social_fitness_app.domain.repository.local.ActivityRepository
import kotlinx.coroutines.flow.Flow


class ActivityRepositoryImpl(private val activityDao: ActivityDao) : ActivityRepository {
    override suspend fun insert(activityEntity: ActivityEntity) {
        activityDao.insert(activityEntity = activityEntity)
    }

    override fun getActivitiesForEvent(eventId: String): Flow<List<ActivityEntity>> {
        return activityDao.getActivitiesForEvent(eventId = eventId)
    }

    override fun getActivitiesForUser(userId: String): Flow<List<ActivityEntity>> {
        return activityDao.getActivitiesForUser(userId = userId)
    }

    override suspend fun deleteActivity(id: String) {
        activityDao.deleteActivity(id = id)
    }
}