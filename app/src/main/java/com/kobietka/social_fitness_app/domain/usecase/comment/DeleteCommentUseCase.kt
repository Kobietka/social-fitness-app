package com.kobietka.social_fitness_app.domain.usecase.comment

import com.kobietka.social_fitness_app.domain.repository.local.CommentRepository
import com.kobietka.social_fitness_app.domain.repository.remote.CommentRemoteRepository
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class DeleteCommentUseCase(
    private val commentRemoteRepository: CommentRemoteRepository,
    private val commentRepository: CommentRepository
) {
    operator fun invoke(commentId: String): Flow<Progress> = flow {
        emit(Progress.Loading)
        when(val result = commentRemoteRepository.deleteComment(commentId = commentId)){
            is NetworkResult.Success -> {
                commentRepository.deleteCommentById(commentId = commentId)
                emit(Progress.Finished)
            }
            is NetworkResult.Failure -> emit(Progress.Error(message = result.message))
            is NetworkResult.Unauthorized -> emit(Progress.Unauthorized)
        }
    }
}