package com.kobietka.social_fitness_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.kobietka.social_fitness_app.data.entity.EventEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface EventDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(eventEntity: EventEntity)

    @Query("select * from event where id = :eventId")
    fun getEventById(eventId: String): Flow<EventEntity>

    @Query("delete from event where id = :eventId")
    suspend fun deleteEventById(eventId: String)

    @Query("select * from event where groupId = :groupId")
    fun getEventsByGroupId(groupId: String): Flow<List<EventEntity>>

}