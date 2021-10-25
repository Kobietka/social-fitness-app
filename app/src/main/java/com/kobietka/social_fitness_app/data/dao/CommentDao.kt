package com.kobietka.social_fitness_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kobietka.social_fitness_app.data.entity.CommentEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(commentEntity: CommentEntity)

    @Query("select * from comment where postId = :postId")
    fun getCommentsForPost(postId: String): Flow<List<CommentEntity>>

    @Query("delete from comment where id = :commentId")
    suspend fun deleteCommentById(commentId: String)

}