package com.kobietka.social_fitness_app.data.repository

import com.kobietka.social_fitness_app.domain.model.User
import com.kobietka.social_fitness_app.domain.repository.remote.GroupRemoteRepository
import com.kobietka.social_fitness_app.network.request.CreateGroupRequest
import com.kobietka.social_fitness_app.network.request.EditGroupRequest
import com.kobietka.social_fitness_app.network.response.GetGroupResponse
import com.kobietka.social_fitness_app.util.NetworkResult
import java.util.*


class TestGroupRemoteRepository : GroupRemoteRepository {
    override suspend fun createGroup(createGroupRequest: CreateGroupRequest): NetworkResult<GetGroupResponse> {
        return NetworkResult.Success(
            data = GetGroupResponse(
                id = UUID.randomUUID().toString(),
                name = "name",
                description = "description",
                owner = User(
                    id = UUID.randomUUID().toString(),
                    nickname = "nickname",
                    email = "email@email.com"
                ),
                invitation = null,
                groupMembers = emptyList(),
                posts = emptyList()
            )
        )
    }

    override suspend fun getGroups(): NetworkResult<List<GetGroupResponse>> {
        return NetworkResult.Success(
            data = listOf(
                GetGroupResponse(
                    id = UUID.randomUUID().toString(),
                    name = "name",
                    description = "description",
                    owner = User(
                        id = UUID.randomUUID().toString(),
                        nickname = "nickname",
                        email = "email@email.com"
                    ),
                    invitation = null,
                    groupMembers = emptyList(),
                    posts = emptyList()
                )
            )
        )
    }

    override suspend fun deleteGroup(groupId: String): NetworkResult<Boolean> {
        return NetworkResult.Success(data = true)
    }

    override suspend fun getGroup(groupId: String): NetworkResult<GetGroupResponse> {
        return NetworkResult.Success(
            data = GetGroupResponse(
                id = UUID.randomUUID().toString(),
                name = "name",
                description = "description",
                owner = User(
                    id = UUID.randomUUID().toString(),
                    nickname = "nickname",
                    email = "email@email.com"
                ),
                invitation = null,
                groupMembers = emptyList(),
                posts = emptyList()
            )
        )
    }

    override suspend fun editGroup(
        groupId: String,
        editGroupRequest: EditGroupRequest
    ): NetworkResult<GetGroupResponse> {
        return NetworkResult.Success(
            data = GetGroupResponse(
                id = UUID.randomUUID().toString(),
                name = "name",
                description = "description",
                owner = User(
                    id = UUID.randomUUID().toString(),
                    nickname = "nickname",
                    email = "email@email.com"
                ),
                invitation = null,
                groupMembers = emptyList(),
                posts = emptyList()
            )
        )
    }
}