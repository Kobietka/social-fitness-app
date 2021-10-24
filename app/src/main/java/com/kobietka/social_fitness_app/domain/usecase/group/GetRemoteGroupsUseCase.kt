package com.kobietka.social_fitness_app.domain.usecase.group

import com.kobietka.social_fitness_app.data.entity.GroupEntity
import com.kobietka.social_fitness_app.data.entity.GroupMemberEntity
import com.kobietka.social_fitness_app.domain.repository.local.GroupMemberRepository
import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository
import com.kobietka.social_fitness_app.domain.repository.remote.GroupRemoteRepository
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetRemoteGroupsUseCase(
    private val groupRemoteRepository: GroupRemoteRepository,
    private val groupRepository: GroupRepository,
    private val groupMemberRepository: GroupMemberRepository
) {
    operator fun invoke(): Flow<Progress> = flow {
        emit(Progress.Loading)
        when(val result = groupRemoteRepository.getGroups()){
            is NetworkResult.Success -> {
                result.data.let { groupResponses ->
                    groupResponses.forEach { groupResponse ->
                        groupRepository.insert(
                            GroupEntity(
                                id = groupResponse.id,
                                ownerId = groupResponse.owner.id,
                                name = groupResponse.name,
                                description = groupResponse.description,
                                invitationCode = groupResponse.invitation?.code
                            )
                        )
                        groupResponse.groupMembers.forEach { memberDto ->
                            groupMemberRepository.insert(
                                GroupMemberEntity(
                                    id = memberDto.id,
                                    groupId = memberDto.groupId,
                                    joinDate = memberDto.assignedAt,
                                    nickname = memberDto.user.nickname,
                                    userId = memberDto.user.id
                                )
                            )
                        }
                    }
                }
                emit(Progress.Finished)
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