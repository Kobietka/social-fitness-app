package com.kobietka.social_fitness_app.domain.usecase.post

import com.kobietka.social_fitness_app.data.entity.toGroupMember
import com.kobietka.social_fitness_app.domain.model.Post
import com.kobietka.social_fitness_app.domain.repository.local.GroupMemberRepository
import com.kobietka.social_fitness_app.domain.repository.local.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class GetPostUseCase(
    private val postRepository: PostRepository,
    private val groupMemberRepository: GroupMemberRepository
) {
    operator fun invoke(postId: String): Flow<Post?> {
        return postRepository.getPostById(postId = postId).map { postEntity ->
            postEntity?.let {
                val groupMembers = groupMemberRepository.getMembersByGroupId(postEntity.groupId).first()
                val member = groupMembers.first { it.userId == postEntity.userId }.toGroupMember()
                Post(
                    id = postEntity.id,
                    content = postEntity.content,
                    user = member,
                    createdAt = postEntity.createdAt
                )
            }
        }
    }
}