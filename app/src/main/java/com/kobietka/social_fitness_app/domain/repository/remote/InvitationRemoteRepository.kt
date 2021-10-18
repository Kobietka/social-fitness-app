package com.kobietka.social_fitness_app.domain.repository.remote

import com.kobietka.social_fitness_app.network.request.CreateInvitationRequest
import com.kobietka.social_fitness_app.network.response.InvitationResponse
import com.kobietka.social_fitness_app.util.Result


interface InvitationRemoteRepository {
    suspend fun createInvitation(createInvitationRequest: CreateInvitationRequest): Result<InvitationResponse>
}