package com.kobietka.social_fitness_app.domain.usecase.group

import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository
import com.kobietka.social_fitness_app.domain.repository.remote.GroupRemoteRepository
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class DeleteGroupUseCase(
    private val groupRemoteRepository: GroupRemoteRepository,
    private val groupRepository: GroupRepository
) {
    operator fun invoke(groupId: String): Flow<Progress> = flow {
        emit(Progress.Loading)

        when(val result = groupRemoteRepository.deleteGroup(groupId = groupId)){
            is NetworkResult.Success -> {
                groupRepository.deleteGroupById(groupId = groupId)
                emit(Progress.Finished)
            }
            is NetworkResult.Failure -> emit(Progress.Error(message = result.message))
            is NetworkResult.Unauthorized -> emit(Progress.Unauthorized)
        }
    }
}