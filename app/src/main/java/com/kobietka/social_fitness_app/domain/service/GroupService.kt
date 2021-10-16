package com.kobietka.social_fitness_app.domain.service

import com.kobietka.social_fitness_app.network.request.CreateGroupRequest
import com.kobietka.social_fitness_app.network.request.EditGroupRequest
import com.kobietka.social_fitness_app.network.response.GetGroupResponse
import retrofit2.http.*


interface GroupService {

    @POST("/api/group")
    suspend fun createGroup(@Body createGroupRequest: CreateGroupRequest): GetGroupResponse

    @GET("/api/group")
    suspend fun getGroups(): List<GetGroupResponse>

    @GET("/api/group/{id}")
    suspend fun getGroup(groupId: String): GetGroupResponse

    @PATCH("/api/group/{id}")
    suspend fun editGroup(
        @Path("id") groupId: String,
        @Body editGroupRequest: EditGroupRequest
    ): GetGroupResponse

    @DELETE("/api/group/{id}")
    suspend fun deleteGroup(@Path("id") groupId: String)

}