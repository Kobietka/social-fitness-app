package com.kobietka.social_fitness_app.domain.repository.remote

import com.kobietka.social_fitness_app.network.request.JoinGroupRequest
import com.kobietka.social_fitness_app.network.response.MemberDto
import com.kobietka.social_fitness_app.util.Result


interface GroupMemberRemoteRepository {
    suspend fun joinGroup(joinGroupRequest: JoinGroupRequest): Result<MemberDto>
    suspend fun leaveGroup(id: String): Result<Boolean>
}