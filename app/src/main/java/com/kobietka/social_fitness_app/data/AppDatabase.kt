package com.kobietka.social_fitness_app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kobietka.social_fitness_app.data.dao.GroupDao
import com.kobietka.social_fitness_app.data.dao.GroupMemberDao
import com.kobietka.social_fitness_app.data.dao.PostDao
import com.kobietka.social_fitness_app.data.dao.UserCredentialsDao
import com.kobietka.social_fitness_app.data.entity.GroupEntity
import com.kobietka.social_fitness_app.data.entity.GroupMemberEntity
import com.kobietka.social_fitness_app.data.entity.PostEntity
import com.kobietka.social_fitness_app.data.entity.UserCredentialsEntity

@Database(
    entities = [
        UserCredentialsEntity::class,
        GroupEntity::class,
        GroupMemberEntity::class,
        PostEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userCredentialsDao(): UserCredentialsDao
    abstract fun groupDao(): GroupDao
    abstract fun groupMemberDao(): GroupMemberDao
    abstract fun postDao(): PostDao
}