package com.kobietka.social_fitness_app.domain.repository.remote

import com.kobietka.social_fitness_app.network.request.CreatePostRequest
import com.kobietka.social_fitness_app.network.request.EditPostRequest
import com.kobietka.social_fitness_app.network.response.PostDto
import com.kobietka.social_fitness_app.util.Result

interface PostRemoteRepository {
    suspend fun getPost(id: String): Result<PostDto>
    suspend fun createPost(createPostRequest: CreatePostRequest): Result<PostDto>
    suspend fun editPost(
        id: String,
        editPostRequest: EditPostRequest
    ): Result<PostDto>
    suspend fun deletePost(id: String): Result<Boolean>
}