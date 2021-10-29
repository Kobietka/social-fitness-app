package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository
import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository
import com.kobietka.social_fitness_app.domain.usecase.group.*
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
    fun provideValidateCreateGroup(): ValidateGroup {
        return ValidateGroup()
    }

    @Provides
    fun provideGetGroupsUseCase(
        groupRepository: GroupRepository
    ): GetGroupsUseCase {
        return GetGroupsUseCase(groupRepository = groupRepository)
    }

}















