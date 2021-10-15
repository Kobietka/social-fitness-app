package com.kobietka.social_fitness_app.data.repository

import com.kobietka.social_fitness_app.data.dao.GroupMemberDao
import com.kobietka.social_fitness_app.data.entity.GroupMemberEntity
import com.kobietka.social_fitness_app.domain.repository.local.GroupMemberRepository
import kotlinx.coroutines.flow.Flow


class GroupMemberRepositoryImpl(
    private val groupMemberDao: GroupMemberDao
) : GroupMemberRepository {
    override suspend fun insert(groupMemberEntity: GroupMemberEntity) {
        groupMemberDao.insert(groupMemberEntity = groupMemberEntity)
    }

    override fun getMembersByGroupId(groupId: String): Flow<List<GroupMemberEntity>> {
        return groupMemberDao.getMembersByGroupId(groupId = groupId)
    }

    override suspend fun deleteMembersByGroupId(groupId: String) {
        groupMemberDao.deleteMembersByGroupId(groupId = groupId)
    }
}