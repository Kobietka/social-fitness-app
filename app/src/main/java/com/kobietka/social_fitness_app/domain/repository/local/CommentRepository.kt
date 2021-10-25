package com.kobietka.social_fitness_app.domain.repository.local

import com.kobietka.social_fitness_app.data.entity.CommentEntity
import kotlinx.coroutines.flow.Flow


interface CommentRepository {
    suspend fun insert(commentEntity: CommentEntity)
    suspend fun getCommentsForPost(postId: String): Flow<List<CommentEntity>>
    suspend fun deleteCommentById(commentId: String)
}