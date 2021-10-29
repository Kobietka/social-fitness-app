package com.kobietka.social_fitness_app.domain.repository.local

import com.kobietka.social_fitness_app.data.entity.GroupEntity
import kotlinx.coroutines.flow.Flow


interface GroupRepository {
    suspend fun insert(groupEntity: GroupEntity)
    fun getAllGroups(): Flow<List<GroupEntity>>
    suspend fun getGroupById(groupId: String): GroupEntity
    suspend fun deleteAllGroups()
    suspend fun deleteGroupById(groupId: String)
}