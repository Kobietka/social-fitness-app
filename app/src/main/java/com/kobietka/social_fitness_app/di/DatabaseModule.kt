package com.kobietka.social_fitness_app.di

import android.content.Context
import androidx.room.Room
import com.kobietka.social_fitness_app.data.AppDatabase
import com.kobietka.social_fitness_app.data.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideUserCredentialsDao(appDatabase: AppDatabase): UserCredentialsDao {
        return appDatabase.userCredentialsDao()
    }

    @Provides
    @Singleton
    fun provideGroupDao(appDatabase: AppDatabase): GroupDao {
        return appDatabase.groupDao()
    }

    @Provides
    @Singleton
    fun provideEventDao(appDatabase: AppDatabase): EventDao {
        return appDatabase.eventDao()
    }

    @Provides
    @Singleton
    fun provideCommentDao(appDatabase: AppDatabase): CommentDao {
        return appDatabase.commentDao()
    }

    @Provides
    @Singleton
    fun providePostDao(appDatabase: AppDatabase): PostDao {
        return appDatabase.postDao()
    }

    @Provides
    @Singleton
    fun provideInvitationDao(appDatabase: AppDatabase): InvitationDao {
        return appDatabase.invitationDao()
    }

    @Provides
    @Singleton
    fun provideGroupMemberDao(appDatabase: AppDatabase): GroupMemberDao {
        return appDatabase.groupMemberDao()
    }

}




















