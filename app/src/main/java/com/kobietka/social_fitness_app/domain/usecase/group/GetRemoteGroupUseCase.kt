package com.kobietka.social_fitness_app.domain.usecase.group

import com.kobietka.social_fitness_app.data.entity.*
import com.kobietka.social_fitness_app.domain.repository.local.*
import com.kobietka.social_fitness_app.domain.repository.remote.GroupRemoteRepository
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetRemoteGroupUseCase(
    private val groupRemoteRepository: GroupRemoteRepository,
    private val groupRepository: GroupRepository,
    private val postRepository: PostRepository,
    private val groupMemberRepository: GroupMemberRepository,
    private val invitationRepository: InvitationRepository,
    private val eventRepository: EventRepository
) {
    operator fun invoke(groupId: String): Flow<Progress> = flow {
        emit(Progress.Loading)

        when(val result = groupRemoteRepository.getGroup(groupId = groupId)){
            is NetworkResult.Success -> {
                result.data.let { groupResponse ->
                    groupRepository.insert(
                        GroupEntity(
                            id = groupResponse.id,
                            name = groupResponse.name,
                            description = groupResponse.description,
                            ownerId = groupResponse.owner.id
                        )
                    )
                    groupResponse.groupMembers.forEach { memberDto ->
                        groupMemberRepository.insert(
                            GroupMemberEntity(
                                id = memberDto.id,
                                userId = memberDto.user.id,
                                groupId = groupResponse.id,
                                nickname = memberDto.user.nickname,
                                joinDate = memberDto.assignedAt
                            )
                        )
                    }
                    groupResponse.invitation?.let { invitation ->
                        invitationRepository.insert(
                            InvitationEntity(
                                id = invitation.id,
                                groupId = groupResponse.id,
                                code = invitation.code
                            )
                        )
                    }
                    groupResponse.posts?.let { posts ->
                        posts.forEach { postDto ->
                            postRepository.insert(
                                PostEntity(
                                    id = postDto.id,
                                    content = postDto.content,
                                    createdAt = postDto.createdAt,
                                    userId = postDto.createdBy.id,
                                    groupId = groupId
                                )
                            )
                        }
                    }
                    groupResponse.events?.let { events ->
                        events.forEach { eventDto ->
                            eventRepository.insert(
                                EventEntity(
                                    id = eventDto.id,
                                    groupId = groupId,
                                    name = eventDto.name,
                                    description = eventDto.description,
                                    pointGoal = eventDto.pointGoal,
                                    pointPerRep = eventDto.pointPerRep,
                                    pointPerMinute = eventDto.pointPerMinute,
                                    startDate = eventDto.startDate,
                                    endDate = eventDto.endDate,
                                    eventType = eventDto.eventType
                                )
                            )
                        }
                    }
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