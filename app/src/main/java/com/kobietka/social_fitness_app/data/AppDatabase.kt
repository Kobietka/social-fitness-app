package com.kobietka.social_fitness_app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kobietka.social_fitness_app.data.dao.*
import com.kobietka.social_fitness_app.data.entity.*

@Database(
    entities = [
        UserCredentialsEntity::class,
        GroupEntity::class,
        GroupMemberEntity::class,
        PostEntity::class,
        InvitationEntity::class,
        CommentEntity::class,
        EventEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userCredentialsDao(): UserCredentialsDao
    abstract fun groupDao(): GroupDao
    abstract fun groupMemberDao(): GroupMemberDao
    abstract fun postDao(): PostDao
    abstract fun invitationDao(): InvitationDao
    abstract fun commentDao(): CommentDao
    abstract fun eventDao(): EventDao
}