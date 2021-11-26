package com.kobietka.social_fitness_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.kobietka.social_fitness_app.data.entity.GroupMemberEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface GroupMemberDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(groupMemberEntity: GroupMemberEntity)

    @Query("select * from groupMember where groupId = :groupId")
    fun getMembersByGroupId(groupId: String): Flow<List<GroupMemberEntity>>

    @Query("delete from groupMember where id = :memberId")
    suspend fun deleteGroupMemberById(memberId: String)

    @Query("delete from groupMember where groupId = :groupId")
    suspend fun deleteMembersByGroupId(groupId: String)

    @Query("select * from groupMember where userId = :userId")
    suspend fun getMemberByUserId(userId: String): GroupMemberEntity

}