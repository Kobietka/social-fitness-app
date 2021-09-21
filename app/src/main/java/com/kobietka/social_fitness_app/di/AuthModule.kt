package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository
import com.kobietka.social_fitness_app.domain.repository.remote.AuthRepository
import com.kobietka.social_fitness_app.domain.usecase.auth.InsertUserCredentialsUseCase
import com.kobietka.social_fitness_app.domain.usecase.auth.LoginUserUseCase
import com.kobietka.social_fitness_app.domain.usecase.auth.RegisterUserUseCase
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

}