package com.kobietka.social_fitness_app.domain.usecase.invitation

import com.kobietka.social_fitness_app.domain.repository.remote.InvitationRemoteRepository
import com.kobietka.social_fitness_app.network.request.CreateInvitationRequest
import com.kobietka.social_fitness_app.network.response.InvitationResponse
import com.kobietka.social_fitness_app.util.Resource
import com.kobietka.social_fitness_app.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CreateInvitationUseCase(private val invitationRepository: InvitationRemoteRepository){
    operator fun invoke(
        groupId: String
    ): Flow<Resource<InvitationResponse>> = flow {
        emit(Resource.Loading<InvitationResponse>())
        val result = invitationRepository.createInvitation(
            createInvitationRequest = CreateInvitationRequest(
                groupId = groupId
            )
        )
        when(result){
            is Result.Success -> {
                result.data?.let { response ->
                    emit(Resource.Success<InvitationResponse>(data = response))
                }
            }
            is Result.Failure -> {
                result.message?.let { message ->
                    emit(Resource.Error<InvitationResponse>(message = message))
                }
            }
            is Result.Unauthorized -> {
                emit(Resource.Unauthorized<InvitationResponse>())
            }
        }
    }
}