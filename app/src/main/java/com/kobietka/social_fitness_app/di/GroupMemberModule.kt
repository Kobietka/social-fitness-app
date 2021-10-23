package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.remote.GroupMemberRemoteRepository
import com.kobietka.social_fitness_app.domain.usecase.groupmember.JoinGroupUseCase
import com.kobietka.social_fitness_app.domain.usecase.groupmember.LeaveGroupUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class GroupMemberModule {

    @Provides
    fun provideJoinGroupUseCase(
        groupMemberRemoteRepository: GroupMemberRemoteRepository
    ): JoinGroupUseCase {
        return JoinGroupUseCase(
            groupMemberRemoteRepository = groupMemberRemoteRepository
        )
    }

    @Provides
    fun provideLeaveGroupUseCase(
        groupMemberRemoteRepository: GroupMemberRemoteRepository
    ): LeaveGroupUseCase {
        return LeaveGroupUseCase(
            groupMemberRemoteRepository = groupMemberRemoteRepository
        )
    }

}


















