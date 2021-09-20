package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.remote.AuthRepository
import com.kobietka.social_fitness_app.domain.service.AuthService
import com.kobietka.social_fitness_app.network.repository.fake.FakeAuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService): AuthRepository {
        // return AuthRepositoryImpl(authService = authService)
        return FakeAuthRepositoryImpl()
    }

}