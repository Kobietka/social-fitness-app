package com.kobietka.social_fitness_app.domain.repository.remote

import com.kobietka.social_fitness_app.network.request.CreateGroupRequest
import com.kobietka.social_fitness_app.network.request.EditGroupRequest
import com.kobietka.social_fitness_app.network.response.GetGroupResponse
import com.kobietka.social_fitness_app.util.NetworkResult


interface GroupRemoteRepository {
    suspend fun createGroup(createGroupRequest: CreateGroupRequest): NetworkResult<GetGroupResponse>
    suspend fun getGroups(): NetworkResult<List<GetGroupResponse>>
    suspend fun deleteGroup(groupId: String): NetworkResult<Boolean>
    suspend fun getGroup(groupId: String): NetworkResult<GetGroupResponse>
    suspend fun editGroup(
        groupId: String,
        editGroupRequest: EditGroupRequest
    ): NetworkResult<GetGroupResponse>
}