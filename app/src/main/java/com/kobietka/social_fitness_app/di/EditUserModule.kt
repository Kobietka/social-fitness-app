package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository
import com.kobietka.social_fitness_app.domain.repository.remote.UpdateUserRemoteRepository
import com.kobietka.social_fitness_app.domain.usecase.edit_user.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class EditUserModule {

    @Provides
    fun provideUpdateUserPasswordUseCase(
        updateUserRemoteRepository: UpdateUserRemoteRepository
    ): UpdateUserPasswordUseCase {
        return UpdateUserPasswordUseCase(
            updateUserRemoteRepository = updateUserRemoteRepository
        )
    }

    @Provides
    fun provideValidateUpdatePasswordUseCase(): ValidateUpdatePasswordUseCase {
        return ValidateUpdatePasswordUseCase()
    }

    @Provides
    fun provideValidateUpdateUserDataUseCase(): ValidateUpdateUserDataUseCase {
        return ValidateUpdateUserDataUseCase()
    }

    @Provides
    fun provideUpdateUserDataUseCase(
        updateUserRemoteRepository: UpdateUserRemoteRepository
    ): UpdateUserDataUseCase {
        return UpdateUserDataUseCase(updateUserRemoteRepository = updateUserRemoteRepository)
    }

    @Provides
    fun provideInsertUpdatedUserDataUseCase(
        userCredentialsRepository: UserCredentialsRepository
    ): InsertUpdatedUserDataUseCase {
        return InsertUpdatedUserDataUseCase(userCredentialsRepository = userCredentialsRepository)
    }

}




















