package com.kobietka.social_fitness_app.domain.service

import com.kobietka.social_fitness_app.network.request.CreateGroupRequest
import com.kobietka.social_fitness_app.network.response.GetGroupResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface GroupService {

    @POST("/api/group")
    suspend fun createGroup(@Body createGroupRequest: CreateGroupRequest): GetGroupResponse

}