package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.local.*
import com.kobietka.social_fitness_app.domain.repository.remote.GroupMemberRemoteRepository
import com.kobietka.social_fitness_app.domain.repository.remote.GroupRemoteRepository
import com.kobietka.social_fitness_app.domain.usecase.group.GetGroupMembersUseCase
import com.kobietka.social_fitness_app.domain.usecase.groupmember.JoinGroupUseCase
import com.kobietka.social_fitness_app.domain.usecase.groupmember.KickGroupMemberUseCase
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
        groupMemberRemoteRepository: GroupMemberRemoteRepository,
        groupMemberRepository: GroupMemberRepository,
        groupRemoteRepository: GroupRemoteRepository,
        groupRepository: GroupRepository,
        invitationRepository: InvitationRepository,
        postRepository: PostRepository,
        commentRepository: CommentRepository
    ): JoinGroupUseCase {
        return JoinGroupUseCase(
            groupMemberRemoteRepository = groupMemberRemoteRepository,
            groupMemberRepository = groupMemberRepository,
            groupRemoteRepository = groupRemoteRepository,
            groupRepository = groupRepository,
            invitationRepository = invitationRepository,
            postRepository = postRepository,
            commentRepository = commentRepository
        )
    }

    @Provides
    fun provideKickGroupMemberUseCase(
        groupMemberRepository: GroupMemberRepository,
        groupMemberRemoteRepository: GroupMemberRemoteRepository
    ): KickGroupMemberUseCase {
        return KickGroupMemberUseCase(
            groupMemberRepository = groupMemberRepository,
            groupMemberRemoteRepository = groupMemberRemoteRepository
        )
    }

    @Provides
    fun provideGetGroupMembersUseCase(
        groupMemberRepository: GroupMemberRepository
    ): GetGroupMembersUseCase {
        return GetGroupMembersUseCase(groupMemberRepository = groupMemberRepository)
    }

    @Provides
    fun provideLeaveGroupUseCase(
        groupMemberRemoteRepository: GroupMemberRemoteRepository,
        groupRepository: GroupRepository
    ): LeaveGroupUseCase {
        return LeaveGroupUseCase(
            groupMemberRemoteRepository = groupMemberRemoteRepository,
            groupRepository = groupRepository
        )
    }

}



















