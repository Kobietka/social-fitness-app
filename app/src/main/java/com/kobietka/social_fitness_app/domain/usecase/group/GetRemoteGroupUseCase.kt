package com.kobietka.social_fitness_app.domain.usecase.group

import com.kobietka.social_fitness_app.data.entity.GroupEntity
import com.kobietka.social_fitness_app.data.entity.GroupMemberEntity
import com.kobietka.social_fitness_app.data.entity.InvitationEntity
import com.kobietka.social_fitness_app.data.entity.PostEntity
import com.kobietka.social_fitness_app.domain.repository.local.GroupMemberRepository
import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository
import com.kobietka.social_fitness_app.domain.repository.local.InvitationRepository
import com.kobietka.social_fitness_app.domain.repository.local.PostRepository
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
    private val invitationRepository: InvitationRepository
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