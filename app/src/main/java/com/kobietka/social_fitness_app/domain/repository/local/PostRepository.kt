package com.kobietka.social_fitness_app.domain.repository.local

import com.kobietka.social_fitness_app.data.entity.PostEntity
import kotlinx.coroutines.flow.Flow


interface PostRepository {
    suspend fun insert(postEntity: PostEntity)
    fun getPostsForGroup(groupId: String): Flow<List<PostEntity>>
    suspend fun deleteAllPosts()
    suspend fun deletePostsFromGroup(groupId: String)
    suspend fun updatePostContent(postId: String, content: String)
    suspend fun deletePostById(postId: String)
    fun getPostById(postId: String): Flow<PostEntity>
}