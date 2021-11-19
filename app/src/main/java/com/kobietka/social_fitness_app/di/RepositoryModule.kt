package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.data.dao.*
import com.kobietka.social_fitness_app.data.repository.*
import com.kobietka.social_fitness_app.domain.repository.local.*
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
    fun provideInvitationRepository(
        invitationDao: InvitationDao
    ): InvitationRepository {
        return InvitationRepositoryImpl(invitationDao = invitationDao)
    }

    @Provides
    @Singleton
    fun provideCommentRepository(
        commentDao: CommentDao
    ): CommentRepository {
        return CommentRepositoryImpl(commentDao = commentDao)
    }

    @Provides
    @Singleton
    fun provideEventMemberRepository(
        eventMemberDao: EventMemberDao
    ): EventMemberRepository {
        return EventMemberRepositoryImpl(eventMemberDao = eventMemberDao)
    }

    @Provides
    @Singleton
    fun provideActivityRepository(
        activityDao: ActivityDao
    ): ActivityRepository {
        return ActivityRepositoryImpl(activityDao = activityDao)
    }

    @Provides
    @Singleton
    fun provideActivityRemoteRepository(
        activityService: ActivityService
    ): ActivityRemoteRepository {
        return ActivityRemoteRepositoryImpl(activityService = activityService)
    }

    @Provides
    @Singleton
    fun provideCommentRemoteRepository(
        commentService: CommentService
    ): CommentRemoteRepository {
        return CommentRemoteRepositoryImpl(commentService = commentService)
    }

    @Provides
    @Singleton
    fun provideEventRemoteRepository(
        eventService: EventService
    ): EventRemoteRepository {
        return EventRemoteRepositoryImpl(eventService = eventService)
    }

    @Provides
    @Singleton
    fun provideEventRepository(
        eventDao: EventDao
    ): EventRepository {
        return EventRepositoryImpl(eventDao = eventDao)
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


















