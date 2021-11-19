package com.kobietka.social_fitness_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kobietka.social_fitness_app.data.entity.EventMemberEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface EventMemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(eventMemberEntity: EventMemberEntity)

    @Query("select * from eventMember where eventId = :eventId")
    fun getEventMembers(eventId: String): Flow<List<EventMemberEntity>>

}