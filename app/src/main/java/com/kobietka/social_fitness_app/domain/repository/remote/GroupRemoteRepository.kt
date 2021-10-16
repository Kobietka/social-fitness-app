package com.kobietka.social_fitness_app.domain.repository.remote

import com.kobietka.social_fitness_app.network.request.CreateGroupRequest
import com.kobietka.social_fitness_app.network.request.EditGroupRequest
import com.kobietka.social_fitness_app.network.response.GetGroupResponse
import com.kobietka.social_fitness_app.util.Result


interface GroupRemoteRepository {
    suspend fun createGroup(createGroupRequest: CreateGroupRequest): Result<GetGroupResponse>
    suspend fun getGroups(): Result<List<GetGroupResponse>>
    suspend fun deleteGroup(groupId: String): Result<Boolean>
    suspend fun getGroup(groupId: String): Result<GetGroupResponse>
    suspend fun editGroup(
        groupId: String,
        editGroupRequest: EditGroupRequest
    ): Result<GetGroupResponse>
}