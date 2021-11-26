package com.kobietka.social_fitness_app.domain.usecase.groupmember

import com.kobietka.social_fitness_app.domain.repository.local.GroupMemberRepository
import com.kobietka.social_fitness_app.domain.repository.remote.GroupMemberRemoteRepository
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class KickGroupMemberUseCase(
    private val groupMemberRemoteRepository: GroupMemberRemoteRepository,
    private val groupMemberRepository: GroupMemberRepository
) {
    operator fun invoke(memberId: String): Flow<Progress> = flow {
        emit(Progress.Loading)
        when(val result = groupMemberRemoteRepository.leaveGroup(id = memberId)){
            is NetworkResult.Success -> {
                groupMemberRepository.deleteGroupMemberById(memberId = memberId)
                emit(Progress.Finished)
            }
            is NetworkResult.Failure -> emit(Progress.Error(message = result.message))
            is NetworkResult.Unauthorized -> emit(Progress.Unauthorized)
        }
    }
}