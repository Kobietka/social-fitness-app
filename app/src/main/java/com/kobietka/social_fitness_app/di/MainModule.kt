package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.local.GroupMemberRepository
import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository
import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository
import com.kobietka.social_fitness_app.domain.repository.remote.GroupRemoteRepository
import com.kobietka.social_fitness_app.domain.usecase.group.CreateGroupUseCase
import com.kobietka.social_fitness_app.domain.usecase.group.InsertGroupDataUseCase
import com.kobietka.social_fitness_app.domain.usecase.group.ValidateCreateGroup
import com.kobietka.social_fitness_app.domain.usecase.main.GetUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    fun provideGetUsersUseCase(
        userCredentialsRepository: UserCredentialsRepository
    ): GetUsersUseCase {
        return GetUsersUseCase(userCredentialsRepository = userCredentialsRepository)
    }

    @Provides
    fun provideValidateCreateGroup(): ValidateCreateGroup {
        return ValidateCreateGroup()
    }

    @Provides
    fun provideCreateGroupUseCase(
        groupRemoteRepository: GroupRemoteRepository
    ): CreateGroupUseCase {
        return CreateGroupUseCase(groupRemoteRepository = groupRemoteRepository)
    }

    @Provides
    fun provideInsertGroupDataUseCase(
        groupRepository: GroupRepository,
        groupMemberRepository: GroupMemberRepository
    ): InsertGroupDataUseCase {
        return InsertGroupDataUseCase(
            groupMemberRepository = groupMemberRepository,
            groupRepository = groupRepository
        )
    }

}
















