package com.kobietka.social_fitness_app.domain.service

import com.kobietka.social_fitness_app.network.request.CreateInvitationRequest
import com.kobietka.social_fitness_app.network.response.InvitationResponse
import retrofit2.http.*


interface InvitationService {

    @POST("/api/invitation")
    suspend fun createInvitation(@Body createInvitationRequest: CreateInvitationRequest): InvitationResponse

    @DELETE("/api/invitation/{id}")
    suspend fun deleteInvitation(@Path("id") id: String)

}