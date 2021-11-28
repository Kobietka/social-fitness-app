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

    override fun getGroupById(groupId: String): Flow<GroupEntity?> {
        return groupDao.getGroupById(groupId = groupId)
    }

    override suspend fun deleteAllGroups() {
        return groupDao.deleteAllGroups()
    }

    override suspend fun deleteGroupById(groupId: String) {
        groupDao.deleteGroupById(groupId = groupId)
    }

    override suspend fun updateGroup(
        id: String,
        name: String,
        description: String,
        ownerId: String
    ) {
        groupDao.updateGroup(
            id = id,
            name = name,
            description = description,
            ownerId = ownerId
        )
    }
}