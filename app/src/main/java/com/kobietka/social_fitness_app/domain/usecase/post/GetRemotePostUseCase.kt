package com.kobietka.social_fitness_app.domain.usecase.post

import com.kobietka.social_fitness_app.data.entity.PostEntity
import com.kobietka.social_fitness_app.domain.repository.local.PostRepository
import com.kobietka.social_fitness_app.domain.repository.remote.PostRemoteRepository
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetRemotePostUseCase(
    private val postRemoteRepository: PostRemoteRepository,
    private val postRepository: PostRepository
) {
    operator fun invoke(
        postId: String,
        groupId: String
    ): Flow<Progress> = flow {
        emit(Progress.Loading)
        when(val result = postRemoteRepository.getPost(id = postId)){
            is NetworkResult.Success -> {
                result.data.let { postDto ->
                    postRepository.insert(
                        PostEntity(
                            id = postDto.id,
                            content = postDto.content,
                            groupId = groupId,
                            userId = postDto.user.id,
                            createdAt = postDto.createdAt
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