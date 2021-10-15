package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.data.dao.GroupDao
import com.kobietka.social_fitness_app.data.dao.GroupMemberDao
import com.kobietka.social_fitness_app.data.dao.UserCredentialsDao
import com.kobietka.social_fitness_app.data.repository.GroupMemberRepositoryImpl
import com.kobietka.social_fitness_app.data.repository.GroupRepositoryImpl
import com.kobietka.social_fitness_app.data.repository.UserCredentialsRepositoryImpl
import com.kobietka.social_fitness_app.domain.repository.local.GroupMemberRepository
import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository
import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository
import com.kobietka.social_fitness_app.domain.repository.remote.AuthRepository
import com.kobietka.social_fitness_app.domain.repository.remote.GroupRemoteRepository
import com.kobietka.social_fitness_app.domain.repository.remote.UpdateUserRemoteRepository
import com.kobietka.social_fitness_app.domain.service.AuthService
import com.kobietka.social_fitness_app.domain.service.GroupService
import com.kobietka.social_fitness_app.domain.service.UpdateUserService
import com.kobietka.social_fitness_app.network.repository.AuthRepositoryImpl
import com.kobietka.social_fitness_app.network.repository.GroupRemoteRepositoryImpl
import com.kobietka.social_fitness_app.network.repository.UpdateUserRemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService): AuthRepository {
        return AuthRepositoryImpl(authService = authService)
    }

    @Provides
    @Singleton
    fun provideUserCredentialsRepository(
        userCredentialsDao: UserCredentialsDao
    ): UserCredentialsRepository {
        return UserCredentialsRepositoryImpl(userCredentialsDao = userCredentialsDao)
    }

    @Provides
    @Singleton
    fun provideGroupRepository(
        groupDao: GroupDao
    ): GroupRepository {
        return GroupRepositoryImpl(groupDao = groupDao)
    }

    @Provides
    @Singleton
    fun provideGroupMemberRepository(
        groupMemberDao: GroupMemberDao
    ): GroupMemberRepository {
        return GroupMemberRepositoryImpl(groupMemberDao = groupMemberDao)
    }

    @Provides
    @Singleton
    fun provideUpdateUserRemoteRepository(
        updateUserService: UpdateUserService
    ): UpdateUserRemoteRepository {
        return UpdateUserRemoteRepositoryImpl(updateUserService = updateUserService)
    }

    @Provides
    @Singleton
    fun provideGroupServiceRemoteRepository(
        groupService: GroupService
    ): GroupRemoteRepository {
        return GroupRemoteRepositoryImpl(groupService = groupService)
    }

}


















