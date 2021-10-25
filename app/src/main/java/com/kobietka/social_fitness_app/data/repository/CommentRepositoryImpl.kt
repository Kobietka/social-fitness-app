package com.kobietka.social_fitness_app.data.repository

import com.kobietka.social_fitness_app.data.dao.CommentDao
import com.kobietka.social_fitness_app.data.entity.CommentEntity
import com.kobietka.social_fitness_app.domain.repository.local.CommentRepository
import kotlinx.coroutines.flow.Flow


class CommentRepositoryImpl(private val commentDao: CommentDao) : CommentRepository {
    override suspend fun insert(commentEntity: CommentEntity) {
        commentDao.insert(commentEntity = commentEntity)
    }

    override suspend fun getCommentsForPost(postId: String): Flow<List<CommentEntity>> {
        return commentDao.getCommentsForPost(postId = postId)
    }

    override suspend fun deleteCommentById(commentId: String) {
        commentDao.deleteCommentById(commentId = commentId)
    }
}