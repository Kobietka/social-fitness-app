package com.kobietka.social_fitness_app.data.repository

import com.kobietka.social_fitness_app.data.dao.GroupMemberDao
import com.kobietka.social_fitness_app.data.entity.GroupMemberEntity
import kotlinx.coroutines.flow.Flow


class GroupMemberRepositoryImpl(
    private val groupMemberDao: GroupMemberDao
) : GroupMemberDao {
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