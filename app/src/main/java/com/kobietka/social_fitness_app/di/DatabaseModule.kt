package com.kobietka.social_fitness_app.di

import android.content.Context
import androidx.room.Room
import com.kobietka.social_fitness_app.data.AppDatabase
import com.kobietka.social_fitness_app.data.dao.GroupDao
import com.kobietka.social_fitness_app.data.dao.GroupMemberDao
import com.kobietka.social_fitness_app.data.dao.UserCredentialsDao
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
    fun provideGroupMemberDao(appDatabase: AppDatabase): GroupMemberDao {
        return appDatabase.groupMemberDao()
    }

}




















