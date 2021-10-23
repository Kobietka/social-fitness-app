package com.kobietka.social_fitness_app.domain.service

import com.kobietka.social_fitness_app.network.request.JoinGroupRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path


interface GroupMemberService {

    @POST("/api/member")
    suspend fun joinGroup(@Body joinGroupRequest: JoinGroupRequest)

    @DELETE("/api/member/{id}")
    suspend fun leaveGroup(@Path("id") id: String)

}