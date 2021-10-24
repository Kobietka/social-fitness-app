package com.kobietka.social_fitness_app.domain.usecase.group

import com.kobietka.social_fitness_app.data.entity.GroupEntity
import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository
import com.kobietka.social_fitness_app.domain.repository.remote.GroupRemoteRepository
import com.kobietka.social_fitness_app.network.request.EditGroupRequest
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class EditGroupUseCase(
    private val groupRemoteRepository: GroupRemoteRepository,
    private val groupRepository: GroupRepository
){
    operator fun invoke(
        groupId: String,
        groupName: String,
        groupDescription: String
    ): Flow<Progress> = flow {
        emit(Progress.Loading)
        val result = groupRemoteRepository.editGroup(
            groupId = groupId,
            editGroupRequest = EditGroupRequest(
                id = groupId,
                name = groupName,
                description = groupDescription
            )
        )
        when(result){
            is NetworkResult.Success -> {
                result.data.let { groupResponse ->
                    groupRepository.insert(
                        GroupEntity(
                            id = groupResponse.id,
                            name = groupResponse.name,
                            description = groupResponse.description,
                            ownerId = groupResponse.owner.id,
                            invitationCode = groupResponse.invitation?.code
                        )
                    )
                    emit(Progress.Finished)
                }
            }
            is NetworkResult.Failure -> {
                emit(Progress.Error(message = result.message))
            }
            is NetworkResult.Unauthorized -> {
                emit(Progress.Unauthorized)
            }
        }
    }
}