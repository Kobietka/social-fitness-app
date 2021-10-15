package com.kobietka.social_fitness_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kobietka.social_fitness_app.data.entity.GroupMemberEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface GroupMemberDao {

    @Insert
    suspend fun insert(groupMemberEntity: GroupMemberEntity)

    @Query("select * from groupMember where groupId = :groupId")
    fun getMembersByGroupId(groupId: String): Flow<List<GroupMemberEntity>>

    @Query("delete from groupMember where groupId = :groupId")
    suspend fun deleteMembersByGroupId(groupId: String)

}