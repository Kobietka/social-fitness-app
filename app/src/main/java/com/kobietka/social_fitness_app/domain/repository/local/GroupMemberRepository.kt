package com.kobietka.social_fitness_app.domain.repository.local

import com.kobietka.social_fitness_app.data.entity.GroupMemberEntity
import kotlinx.coroutines.flow.Flow


interface GroupMemberRepository {
    suspend fun insert(groupMemberEntity: GroupMemberEntity)
    fun getMembersByGroupId(groupId: String): Flow<List<GroupMemberEntity>>
    suspend fun deleteMembersByGroupId(groupId: String)
}