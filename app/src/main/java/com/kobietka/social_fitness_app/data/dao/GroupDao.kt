package com.kobietka.social_fitness_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kobietka.social_fitness_app.data.entity.GroupEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface GroupDao {

    @Insert
    suspend fun insert(groupEntity: GroupEntity)

    @Query("select * from `group`")
    fun getAllGroups(): Flow<List<GroupEntity>>

    @Query("delete from `group`")
    suspend fun deleteAllGroups()

    @Query("delete from `group` where id = :groupId")
    suspend fun deleteGroupById(groupId: String)

}