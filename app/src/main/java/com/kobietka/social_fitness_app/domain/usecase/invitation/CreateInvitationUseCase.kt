package com.kobietka.social_fitness_app.domain.usecase.invitation

import com.kobietka.social_fitness_app.data.entity.InvitationEntity
import com.kobietka.social_fitness_app.domain.repository.local.InvitationRepository
import com.kobietka.social_fitness_app.domain.repository.remote.InvitationRemoteRepository
import com.kobietka.social_fitness_app.network.request.CreateInvitationRequest
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class CreateInvitationUseCase(
    private val invitationRemoteRepository: InvitationRemoteRepository,
    private val invitationRepository: InvitationRepository
){
    operator fun invoke(
        groupId: String
    ): Flow<Progress> = flow {
        emit(Progress.Loading)
        val result = invitationRemoteRepository.createInvitation(
            createInvitationRequest = CreateInvitationRequest(
                groupId = groupId
            )
        )
        when(result){
            is NetworkResult.Success -> {
                result.data.let { invitationResponse ->
                    invitationRepository.insert(
                        InvitationEntity(
                            id = invitationResponse.id,
                            groupId = groupId,
                            code = invitationResponse.code
                        )
                    )
                    emit(Progress.Finished)
                }
            }
            is NetworkResult.Failure -> emit(Progress.Error(message = result.message))
            is NetworkResult.Unauthorized -> emit(Progress.Unauthorized)
        }
    }
}