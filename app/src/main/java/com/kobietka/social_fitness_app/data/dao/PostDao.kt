package com.kobietka.social_fitness_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.kobietka.social_fitness_app.data.entity.PostEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PostDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(postEntity: PostEntity)

    @Query("select * from post where groupId = :groupId")
    fun getPostsForGroup(groupId: String): Flow<List<PostEntity>>

    @Query("delete from post")
    suspend fun deleteAllPosts()

    @Query("delete from post where id = :postId")
    suspend fun deletePostById(postId: String)

    @Query("select * from post where id = :postId")
    fun getPostById(postId: String): Flow<PostEntity?>

    @Query("delete from post where groupId = :groupId")
    suspend fun deletePostsFromGroup(groupId: String)

    @Query("update post set content = :content where id = :postId")
    suspend fun updatePostContent(postId: String, content: String)

}


























