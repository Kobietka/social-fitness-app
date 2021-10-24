package com.kobietka.social_fitness_app.domain.usecase.invitation

import com.kobietka.social_fitness_app.domain.repository.local.InvitationRepository
import com.kobietka.social_fitness_app.domain.repository.remote.InvitationRemoteRepository
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteInvitationUseCase(
    private val invitationRemoteRepository: InvitationRemoteRepository,
    private val invitationRepository: InvitationRepository
){
    operator fun invoke(
        id: String
    ): Flow<Progress> = flow {
        emit(Progress.Loading)
        when(val result = invitationRemoteRepository.deleteInvitation(id = id)){
            is NetworkResult.Success -> {
                invitationRepository.deleteInvitationById(id = id)
                emit(Progress.Finished)
            }
            is NetworkResult.Failure -> emit(Progress.Error(message = result.message))
            is NetworkResult.Unauthorized -> emit(Progress.Unauthorized)
        }
    }
}