package com.kobietka.social_fitness_app.domain.usecase.post

import com.kobietka.social_fitness_app.domain.repository.local.PostRepository
import com.kobietka.social_fitness_app.domain.repository.remote.PostRemoteRepository
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class DeletePostUseCase(
    private val postRemoteRepository: PostRemoteRepository,
    private val postRepository: PostRepository
) {
    operator fun invoke(postId: String): Flow<Progress> = flow {
        emit(Progress.Loading)
        when(val result = postRemoteRepository.deletePost(id = postId)){
            is NetworkResult.Success -> {
                postRepository.deletePostById(postId = postId)
                emit(Progress.Finished)
            }
            is NetworkResult.Failure -> emit(Progress.Error(message = result.message))
            is NetworkResult.Unauthorized -> emit(Progress.Unauthorized)
        }
    }
}