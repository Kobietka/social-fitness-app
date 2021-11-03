package com.kobietka.social_fitness_app.domain.usecase.comment

import com.kobietka.social_fitness_app.data.entity.CommentEntity
import com.kobietka.social_fitness_app.domain.repository.local.CommentRepository
import com.kobietka.social_fitness_app.domain.repository.remote.CommentRemoteRepository
import com.kobietka.social_fitness_app.network.request.EditCommentRequest
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class EditCommentUseCase(
    private val commentRemoteRepository: CommentRemoteRepository,
    private val commentRepository: CommentRepository
) {
    operator fun invoke(
        postId: String,
        commentId: String,
        content: String
    ): Flow<Progress> = flow {
        emit(Progress.Loading)
        val result = commentRemoteRepository.editComment(
            commentId = commentId,
            editCommentRequest = EditCommentRequest(
                id = commentId,
                content = content
            )
        )
        when(result){
            is NetworkResult.Success -> {
                result.data.let { commentDto ->
                    commentRepository.insert(
                        CommentEntity(
                            id = commentDto.id,
                            content = commentDto.content,
                            postId = postId,
                            createdAt = commentDto.createdAt,
                            userId = commentDto.createdBy.id
                        )
                    )
                    emit(Progress.Finished)
                }
            }
            is NetworkResult.Failure -> emit(Progress.Error(message = result.message))
            is NetworkResult.Unauthorized -> emit(Progress.Unauthorized)
        }
    }
}





















