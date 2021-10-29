package com.kobietka.social_fitness_app.domain.usecase.post

import com.kobietka.social_fitness_app.data.entity.PostEntity
import com.kobietka.social_fitness_app.domain.repository.local.PostRepository
import kotlinx.coroutines.flow.Flow


class GetPostsForGroupUseCase(
    private val postRepository: PostRepository
) {
    operator fun invoke(groupId: String): Flow<List<PostEntity>> {
        return postRepository.getPostsForGroup(groupId = groupId)
    }
}