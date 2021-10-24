package com.kobietka.social_fitness_app.domain.repository.remote

import com.kobietka.social_fitness_app.network.request.CreatePostRequest
import com.kobietka.social_fitness_app.network.request.EditPostRequest
import com.kobietka.social_fitness_app.network.response.PostDto
import com.kobietka.social_fitness_app.util.NetworkResult

interface PostRemoteRepository {
    suspend fun getPost(id: String): NetworkResult<PostDto>
    suspend fun createPost(createPostRequest: CreatePostRequest): NetworkResult<PostDto>
    suspend fun editPost(
        id: String,
        editPostRequest: EditPostRequest
    ): NetworkResult<PostDto>
    suspend fun deletePost(id: String): NetworkResult<Boolean>
}