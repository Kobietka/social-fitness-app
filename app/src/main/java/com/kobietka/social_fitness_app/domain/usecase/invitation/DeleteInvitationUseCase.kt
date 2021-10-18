package com.kobietka.social_fitness_app.domain.usecase.invitation

import com.kobietka.social_fitness_app.domain.repository.remote.InvitationRemoteRepository
import com.kobietka.social_fitness_app.util.Resource
import com.kobietka.social_fitness_app.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteInvitationUseCase(private val invitationRepository: InvitationRemoteRepository){
    operator fun invoke(
        id: String
    ): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading<Boolean>())
        when(val result = invitationRepository.deleteInvitation(id = id)){
            is Result.Success -> {
                result.data?.let { response ->
                    emit(Resource.Success<Boolean>(data = response))
                }
            }
            is Result.Failure -> {
                result.message?.let { message ->
                    emit(Resource.Error<Boolean>(message = message))
                }
            }
            is Result.Unauthorized -> {
                emit(Resource.Unauthorized<Boolean>())
            }
        }
    }
}