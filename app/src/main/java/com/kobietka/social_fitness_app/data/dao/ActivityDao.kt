package com.kobietka.social_fitness_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kobietka.social_fitness_app.data.entity.ActivityEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ActivityDao {

    @Insert
    suspend fun insert(activityEntity: ActivityEntity)

    @Query("select * from activity where eventId = :eventId")
    fun getActivitiesForEvent(eventId: String): Flow<List<ActivityEntity>>

    @Query("select * from activity where userId = :userId")
    fun getActivitiesForUser(userId: String): Flow<List<ActivityEntity>>

    @Query("delete from activity where id = :id")
    suspend fun deleteActivity(id: String)

}