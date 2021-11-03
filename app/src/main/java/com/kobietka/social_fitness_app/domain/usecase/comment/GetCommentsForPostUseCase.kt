package com.kobietka.social_fitness_app.domain.usecase.comment

import com.kobietka.social_fitness_app.data.entity.toGroupMember
import com.kobietka.social_fitness_app.domain.model.Comment
import com.kobietka.social_fitness_app.domain.repository.local.CommentRepository
import com.kobietka.social_fitness_app.domain.repository.local.GroupMemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetCommentsForPostUseCase(
    val commentRepository: CommentRepository,
    val groupMemberRepository: GroupMemberRepository
) {
    operator fun invoke(postId: String): Flow<List<Comment>> {
        return commentRepository.getCommentsForPost(postId = postId).map { commentEntities ->
            commentEntities.map { commentEntity ->
                val groupMember = groupMemberRepository.getMemberByUserId(
                    userId = commentEntity.userId
                ).toGroupMember()
                Comment(
                    id = commentEntity.id,
                    user = groupMember,
                    content = commentEntity.content,
                    createdAt = commentEntity.createdAt
                )
            }
        }
    }
}