package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.remote.InvitationRemoteRepository
import com.kobietka.social_fitness_app.domain.usecase.invitation.CreateInvitationUseCase
import com.kobietka.social_fitness_app.domain.usecase.invitation.DeleteInvitationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class InvitationModule {

    @Provides
    fun provideCreateInvitationUseCase(
        invitationRemoteRepository: InvitationRemoteRepository
    ): CreateInvitationUseCase {
        return CreateInvitationUseCase(invitationRepository = invitationRemoteRepository)
    }

    @Provides
    fun provideDeleteInvitationUseCase(
        invitationRemoteRepository: InvitationRemoteRepository
    ): DeleteInvitationUseCase {
        return DeleteInvitationUseCase(invitationRepository = invitationRemoteRepository)
    }

}