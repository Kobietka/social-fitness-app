package com.kobietka.social_fitness_app.data.repository

import com.kobietka.social_fitness_app.data.entity.GroupEntity
import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class TestGroupRepository : GroupRepository {
    private val groups = mutableListOf<GroupEntity>()

    override suspend fun insert(groupEntity: GroupEntity) {
        groups.add(groupEntity)
    }

    override fun getAllGroups(): Flow<List<GroupEntity>> {
        return flow { emit(groups) }
    }

    override fun getGroupById(groupId: String): Flow<GroupEntity> {
        return flow { emit(groups.first { it.id == groupId } ) }
    }

    override suspend fun deleteAllGroups() {
        groups.clear()
    }

    override suspend fun deleteGroupById(groupId: String) {
        groups.removeIf { it.id == groupId }
    }
}