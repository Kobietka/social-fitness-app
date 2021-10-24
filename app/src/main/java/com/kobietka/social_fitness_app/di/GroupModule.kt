package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.local.GroupMemberRepository
import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository
import com.kobietka.social_fitness_app.domain.repository.local.PostRepository
import com.kobietka.social_fitness_app.domain.repository.remote.GroupRemoteRepository
import com.kobietka.social_fitness_app.domain.usecase.group.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class GroupModule {

    @Provides
    fun provideDeleteGroupUseCase(
        groupRemoteRepository: GroupRemoteRepository,
        groupRepository: GroupRepository,
    ): DeleteGroupUseCase {
        return DeleteGroupUseCase(
            groupRepository = groupRepository,
            groupRemoteRepository = groupRemoteRepository
        )
    }

    @Provides
    fun provideGetRemoteGroupsUseCase(
        groupRemoteRepository: GroupRemoteRepository,
        groupRepository: GroupRepository,
        groupMemberRepository: GroupMemberRepository
    ): GetRemoteGroupsUseCase {
        return GetRemoteGroupsUseCase(
            groupRemoteRepository = groupRemoteRepository,
            groupMemberRepository = groupMemberRepository,
            groupRepository = groupRepository
        )
    }

    @Provides
    fun provideGetRemoteGroupUseCase(
        groupRemoteRepository: GroupRemoteRepository,
        postRepository: PostRepository,
        groupRepository: GroupRepository
    ): GetRemoteGroupUseCase {
        return GetRemoteGroupUseCase(
            groupRepository = groupRepository,
            groupRemoteRepository = groupRemoteRepository,
            postRepository = postRepository
        )
    }

    @Provides
    fun provideCreateGroupUseCase(
        groupRemoteRepository: GroupRemoteRepository,
        groupRepository: GroupRepository,
        groupMemberRepository: GroupMemberRepository
    ): CreateGroupUseCase {
        return CreateGroupUseCase(
            groupRemoteRepository = groupRemoteRepository,
            groupRepository = groupRepository,
            groupMemberRepository = groupMemberRepository
        )
    }

    @Provides
    fun provideEditGroupUseCase(
        groupRemoteRepository: GroupRemoteRepository,
        groupRepository: GroupRepository,
    ): EditGroupUseCase {
        return EditGroupUseCase(
            groupRepository = groupRepository,
            groupRemoteRepository = groupRemoteRepository
        )
    }

}

























