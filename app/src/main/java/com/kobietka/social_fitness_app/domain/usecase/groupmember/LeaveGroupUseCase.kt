package com.kobietka.social_fitness_app.domain.usecase.groupmember

import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository
import com.kobietka.social_fitness_app.domain.repository.remote.GroupMemberRemoteRepository
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class LeaveGroupUseCase(
    private val groupMemberRemoteRepository: GroupMemberRemoteRepository,
    private val groupRepository: GroupRepository
){
    operator fun invoke(
        id: String
    ): Flow<Progress> = flow {
        emit(Progress.Loading)
        when(val result = groupMemberRemoteRepository.leaveGroup(id = id)){
            is NetworkResult.Success -> {
                groupRepository.deleteGroupById(groupId = id)
                emit(Progress.Finished)
            }
            is NetworkResult.Failure -> emit(Progress.Error(message = result.message))
            is NetworkResult.Unauthorized -> emit(Progress.Unauthorized)
        }
    }
}