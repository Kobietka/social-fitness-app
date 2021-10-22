package com.kobietka.social_fitness_app.di

import android.content.Context
import androidx.room.Room
import com.kobietka.social_fitness_app.data.AppDatabase
import com.kobietka.social_fitness_app.data.dao.GroupDao
import com.kobietka.social_fitness_app.data.dao.GroupMemberDao
import com.kobietka.social_fitness_app.data.dao.UserCredentialsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


@Module
@TestInstallIn(
    replaces = [DatabaseModule::class],
    components = [SingletonComponent::class]
)
class TestDatabaseModuleForAuth {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
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