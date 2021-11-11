package com.kobietka.social_fitness_app.domain.service

import com.kobietka.social_fitness_app.network.request.CreateCommentRequest
import com.kobietka.social_fitness_app.network.request.EditCommentRequest
import com.kobietka.social_fitness_app.network.response.CommentDto
import retrofit2.Response
import retrofit2.http.*


interface CommentService {

    @POST("/api/comment")
    suspend fun createComment(@Body createCommentRequest: CreateCommentRequest): CommentDto

    @DELETE("/api/comment/{id}")
    suspend fun deleteComment(@Path("id") commentId: String): Response<Unit>

    @PATCH("/api/comment/{id}")
    suspend fun editComment(
        @Path("id") commentId: String,
        @Body editCommentRequest: EditCommentRequest
    ): CommentDto

}