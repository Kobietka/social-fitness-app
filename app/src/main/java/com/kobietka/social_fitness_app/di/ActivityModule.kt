package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.local.ActivityRepository
import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository
import com.kobietka.social_fitness_app.domain.repository.remote.ActivityRemoteRepository
import com.kobietka.social_fitness_app.domain.usecase.activity.CreateActivityUseCase
import com.kobietka.social_fitness_app.domain.usecase.activity.DeleteActivityUseCase
import com.kobietka.social_fitness_app.domain.usecase.activity.ValidateActivityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class ActivityModule {

    @Provides
    fun provideCreateActivityUseCase(
        activityRemoteRepository: ActivityRemoteRepository,
        activityRepository: ActivityRepository,
        userCredentialsRepository: UserCredentialsRepository
    ): CreateActivityUseCase {
        return CreateActivityUseCase(
            activityRemoteRepository = activityRemoteRepository,
            activityRepository = activityRepository,
            userCredentialsRepository = userCredentialsRepository
        )
    }

    @Provides
    fun provideValidateActivityUseCase(): ValidateActivityUseCase {
        return ValidateActivityUseCase()
    }

    @Provides
    fun provideDeleteActivityUseCase(
        activityRemoteRepository: ActivityRemoteRepository,
        activityRepository: ActivityRepository,
    ): DeleteActivityUseCase {
        return DeleteActivityUseCase(
            activityRemoteRepository = activityRemoteRepository,
            activityRepository = activityRepository
        )
    }

}