package com.kobietka.social_fitness_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.kobietka.social_fitness_app.data.entity.GroupEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface GroupDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(groupEntity: GroupEntity)

    @Query("select * from `group`")
    fun getAllGroups(): Flow<List<GroupEntity>>

    @Query("select * from `group` where id = :groupId")
    fun getGroupById(groupId: String): Flow<GroupEntity>

    @Query("update `group` set name = :name, description = :description, ownerId = :ownerId where id = :id")
    suspend fun updateGroup(
        id: String,
        name: String,
        description: String,
        ownerId: String
    )

    @Query("delete from `group`")
    suspend fun deleteAllGroups()

    @Query("delete from `group` where id = :groupId")
    suspend fun deleteGroupById(groupId: String)

}