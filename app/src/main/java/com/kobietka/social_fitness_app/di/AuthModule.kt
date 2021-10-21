package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.local.GroupRepository
import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository
import com.kobietka.social_fitness_app.domain.repository.remote.AuthRepository
import com.kobietka.social_fitness_app.domain.usecase.auth.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    fun provideRegisterUseCase(authRepository: AuthRepository): RegisterUserUseCase {
        return RegisterUserUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUserUseCase {
        return LoginUserUseCase(authRepository = authRepository)
    }

    @Provides
    fun provideInsertUserCredentialsUseCase(
        userCredentialsRepository: UserCredentialsRepository
    ): InsertUserCredentialsUseCase {
        return InsertUserCredentialsUseCase(userCredentialsRepository = userCredentialsRepository)
    }

    @Provides
    fun provideValidateUserRegisterData(): ValidateUserRegisterDataUseCase {
        return ValidateUserRegisterDataUseCase()
    }

    @Provides
    fun provideLogoutUserUseCase(
        userCredentialsRepository: UserCredentialsRepository,
        groupRepository: GroupRepository
    ): LogoutUserUseCase {
        return LogoutUserUseCase(
            userCredentialsRepository = userCredentialsRepository,
            groupRepository = groupRepository
        )
    }

}