package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.data.dao.GroupDao
import com.kobietka.social_fitness_app.data.dao.GroupMemberDao
import com.kobietka.social_fitness_app.data.dao.PostDao
import com.kobietka.social_fitness_app.data.dao.UserCredentialsDao
import com.kobietka.social_fitness_app.data.repository.GroupMemberRepositoryImpl
import com.kobietka.social_fitness_app.data.repository.GroupRepositoryImpl
import com.kobietka.social_fitness_app.data.repository.PostRepositoryImpl
import com.kobietka.social_fitness_app.data.repository.UserCredentialsRepositoryImpl
import com.kobietka.social_fitness_app.domain.repository.local.GroupMemberRepository
import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository
import com.kobietka.social_fitness_app.domain.repository.local.PostRepository
import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository
import com.kobietka.social_fitness_app.domain.repository.remote.*
import com.kobietka.social_fitness_app.domain.service.*
import com.kobietka.social_fitness_app.network.repository.*
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
    fun providePostRepository(
        postDao: PostDao
    ): PostRepository {
        return PostRepositoryImpl(postDao = postDao)
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

    @Provides
    @Singleton
    fun providePostRemoteRepository(
        postService: PostService
    ): PostRemoteRepository {
        return PostRemoteRepositoryImpl(postService = postService)
    }

    @Provides
    @Singleton
    fun provideGroupMemberRemoteRepository(
        groupMemberService: GroupMemberService
    ): GroupMemberRemoteRepository {
        return GroupMemberRemoteRepositoryImpl(groupMemberService = groupMemberService)
    }

    @Provides
    @Singleton
    fun provideInvitationServiceRemoteRepository(
        invitationService: InvitationService
    ): InvitationRemoteRepository {
        return InvitationRemoteRepositoryImpl(
            invitationService = invitationService
        )
    }

}


















