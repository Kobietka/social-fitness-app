package com.kobietka.social_fitness_app.data.repository

import com.kobietka.social_fitness_app.data.dao.PostDao
import com.kobietka.social_fitness_app.data.entity.PostEntity
import com.kobietka.social_fitness_app.domain.repository.local.PostRepository
import kotlinx.coroutines.flow.Flow


class PostRepositoryImpl(private val postDao: PostDao) : PostRepository {
    override suspend fun insert(postEntity: PostEntity) {
        postDao.insert(postEntity = postEntity)
    }

    override fun getPostsForGroup(groupId: String): Flow<List<PostEntity>> {
        return postDao.getPostsForGroup(groupId = groupId)
    }

    override suspend fun deleteAllPosts() {
        postDao.deleteAllPosts()
    }

    override suspend fun deletePostsFromGroup(groupId: String) {
        postDao.deletePostsFromGroup(groupId = groupId)
    }
}