package com.kobietka.social_fitness_app.domain.usecase.groupmember

import com.kobietka.social_fitness_app.data.entity.*
import com.kobietka.social_fitness_app.domain.repository.local.*
import com.kobietka.social_fitness_app.domain.repository.remote.GroupMemberRemoteRepository
import com.kobietka.social_fitness_app.domain.repository.remote.GroupRemoteRepository
import com.kobietka.social_fitness_app.network.request.JoinGroupRequest
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class JoinGroupUseCase(
    private val groupMemberRemoteRepository: GroupMemberRemoteRepository,
    private val groupMemberRepository: GroupMemberRepository,
    private val groupRemoteRepository: GroupRemoteRepository,
    private val groupRepository: GroupRepository,
    private val invitationRepository: InvitationRepository,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository
){
    operator fun invoke(
        code: String
    ): Flow<Progress> = flow {
        emit(Progress.Loading)
        val result = groupMemberRemoteRepository.joinGroup(
            joinGroupRequest = JoinGroupRequest(code = code)
        )
        when(result){
            is NetworkResult.Success -> {
                result.data.let { memberDto ->
                    when(val groupResult = groupRemoteRepository.getGroup(groupId = memberDto.groupId)){
                        is NetworkResult.Success -> {
                            groupResult.data.let { groupResponse ->
                                groupRepository.insert(
                                    GroupEntity(
                                        id = groupResponse.id,
                                        ownerId = groupResponse.owner.id,
                                        name = groupResponse.name,
                                        description = groupResponse.description
                                    )
                                )
                                groupResponse.groupMembers.forEach { groupMemberDto ->
                                    groupMemberRepository.insert(
                                        GroupMemberEntity(
                                            id = groupMemberDto.id,
                                            userId = groupMemberDto.user.id,
                                            groupId = groupResponse.id,
                                            nickname = groupMemberDto.user.nickname,
                                            joinDate = groupMemberDto.assignedAt
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
                                                groupId = groupResponse.id,
                                                content = postDto.content,
                                                userId = postDto.user.id,
                                                createdAt = postDto.createdAt
                                            )
                                        )
                                        postDto.comments?.let { comments ->
                                            comments.forEach { commentDto ->
                                                commentRepository.insert(
                                                    CommentEntity(
                                                        id = commentDto.id,
                                                        content = commentDto.content,
                                                        createdAt = commentDto.createdAt,
                                                        postId = postDto.id,
                                                        userId = commentDto.user.id
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                                emit(Progress.Finished)
                            }
                        }
                        is NetworkResult.Failure -> emit(Progress.Error(message = groupResult.message))
                        is NetworkResult.Unauthorized -> emit(Progress.Unauthorized)
                    }
                }
            }
            is NetworkResult.Failure -> emit(Progress.Error(message = result.message))
            is NetworkResult.Unauthorized -> emit(Progress.Unauthorized)
        }
    }
}