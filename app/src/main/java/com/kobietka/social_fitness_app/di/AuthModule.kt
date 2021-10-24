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
    fun provideLoginUseCase(
        authRepository: AuthRepository,
        userCredentialsRepository: UserCredentialsRepository
    ): LoginUserUseCase {
        return LoginUserUseCase(
            authRepository = authRepository,
            userCredentialsRepository = userCredentialsRepository
        )
    }

    @Provides
    fun provideInsertUserCredentialsUseCase(
        userCredentialsRepository: UserCredentialsRepository
    ): InsertUserCredentialsUseCase {
        return InsertUserCredentialsUseCase(userCredentialsRepository = userCredentialsRepository)
    }

    @Provides
    fun provideValidateUserRegisterData(): ValidateRegisterUseCase {
        return ValidateRegisterUseCase()
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