package com.kobietka.social_fitness_app.domain.service

import com.kobietka.social_fitness_app.network.request.CreateInvitationRequest
import com.kobietka.social_fitness_app.network.response.InvitationResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface InvitationService {

    @POST("/api/invitation")
    suspend fun createInvitation(@Body createInvitationRequest: CreateInvitationRequest): InvitationResponse

}