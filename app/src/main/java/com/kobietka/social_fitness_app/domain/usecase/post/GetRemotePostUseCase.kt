package com.kobietka.social_fitness_app.domain.usecase.post

import android.util.Log
import com.kobietka.social_fitness_app.data.entity.CommentEntity
import com.kobietka.social_fitness_app.data.entity.PostEntity
import com.kobietka.social_fitness_app.domain.repository.local.CommentRepository
import com.kobietka.social_fitness_app.domain.repository.local.PostRepository
import com.kobietka.social_fitness_app.domain.repository.remote.PostRemoteRepository
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetRemotePostUseCase(
    private val postRemoteRepository: PostRemoteRepository,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository
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
                            userId = postDto.createdBy.id,
                            createdAt = postDto.createdAt
                        )
                    )
                    postDto.comments?.let { commentDtos ->
                        commentDtos.forEach { commentDto ->
                            Log.e("comment", commentDto.content)
                            commentRepository.insert(
                                CommentEntity(
                                    id = commentDto.id,
                                    content = commentDto.content,
                                    postId = postId,
                                    userId = commentDto.createdBy.id,
                                    createdAt = commentDto.createdAt
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