package com.kobietka.social_fitness_app.domain.service

import com.kobietka.social_fitness_app.network.request.CreatePostRequest
import com.kobietka.social_fitness_app.network.request.EditPostRequest
import com.kobietka.social_fitness_app.network.response.PostDto
import retrofit2.http.*


interface PostService {

    @GET("/api/post/{id}")
    suspend fun getPost(@Path("id") id: String): PostDto

    @POST("/api/post")
    suspend fun createPost(@Body createPostRequest: CreatePostRequest): PostDto

    @PATCH("/api/post/{id}")
    suspend fun editPost(
        @Path("id") id: String,
        @Body editPostRequest: EditPostRequest
    ): PostDto

    @DELETE("/api/post/{id}")
    suspend fun deletePost(@Path("id") id: String)

}
