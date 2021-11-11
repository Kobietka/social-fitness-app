package com.kobietka.social_fitness_app.domain.usecase.post

import com.kobietka.social_fitness_app.data.entity.PostEntity
import com.kobietka.social_fitness_app.data.entity.toGroupMember
import com.kobietka.social_fitness_app.domain.model.GroupMember
import com.kobietka.social_fitness_app.domain.model.Post
import com.kobietka.social_fitness_app.domain.repository.local.GroupMemberRepository
import kotlinx.coroutines.flow.first


class MatchPostsWithMembersUseCase(private val groupMemberRepository: GroupMemberRepository) {
    suspend operator fun invoke(
        groupId: String,
        postEntities: List<PostEntity>
    ): List<Post> {
        val posts = mutableListOf<Post>()
        val groupMemberEntities = groupMemberRepository.getMembersByGroupId(groupId = groupId).first()
        postEntities.forEach { postEntity ->
            val groupMemberEntity = groupMemberEntities.find { it.userId == postEntity.userId }
            posts.add(
                Post(
                    id = postEntity.id,
                    content = postEntity.content,
                    createdAt = postEntity.createdAt,
                    user = groupMemberEntity?.toGroupMember() ?: GroupMember(id = "", userId = "", nickname = "User not found")
                )
            )
        }
        return posts.reversed()
    }
}