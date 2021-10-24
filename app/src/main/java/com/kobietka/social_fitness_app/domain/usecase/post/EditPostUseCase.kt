package com.kobietka.social_fitness_app.domain.usecase.post

import com.kobietka.social_fitness_app.domain.repository.local.PostRepository
import com.kobietka.social_fitness_app.domain.repository.remote.PostRemoteRepository
import com.kobietka.social_fitness_app.network.request.EditPostRequest
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class EditPostUseCase(
    private val postRemoteRepository: PostRemoteRepository,
    private val postRepository: PostRepository
) {
    operator fun invoke(
        postId: String,
        content: String
    ): Flow<Progress> = flow {
        emit(Progress.Loading)
        val result = postRemoteRepository.editPost(
            id = postId,
            editPostRequest = EditPostRequest(
                id = postId,
                content = content
            )
        )
        when(result){
            is NetworkResult.Success -> {
                result.data.let { postDto ->
                    postRepository.updatePostContent(
                        postId = postDto.id,
                        content = postDto.content
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

























