package com.kobietka.social_fitness_app.data.repository

import com.kobietka.social_fitness_app.data.dao.GroupDao
import com.kobietka.social_fitness_app.data.entity.GroupEntity
import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository
import kotlinx.coroutines.flow.Flow


class GroupRepositoryImpl(
    private val groupDao: GroupDao
) : GroupRepository {
    override suspend fun insert(groupEntity: GroupEntity) {
        groupDao.insert(groupEntity = groupEntity)
    }

    override fun getAllGroups(): Flow<List<GroupEntity>> {
        return groupDao.getAllGroups()
    }

    override suspend fun deleteAllGroups() {
        return groupDao.deleteAllGroups()
    }

    override suspend fun deleteGroupById(groupId: String) {
        groupDao.deleteGroupById(groupId = groupId)
    }
}