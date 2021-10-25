package com.kobietka.social_fitness_app.domain.repository.remote

import com.kobietka.social_fitness_app.network.request.CreateCommentRequest
import com.kobietka.social_fitness_app.network.request.EditCommentRequest
import com.kobietka.social_fitness_app.network.response.CommentDto
import com.kobietka.social_fitness_app.util.NetworkResult


interface CommentRemoteRepository {
    suspend fun createComment(createCommentRequest: CreateCommentRequest): NetworkResult<CommentDto>
    suspend fun deleteComment(commentId: String): NetworkResult<Boolean>
    suspend fun editComment(
        commentId: String,
        editCommentRequest: EditCommentRequest
    ): NetworkResult<CommentDto>
}