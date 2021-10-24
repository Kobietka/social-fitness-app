package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.local.InvitationRepository
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
        invitationRemoteRepository: InvitationRemoteRepository,
        invitationRepository: InvitationRepository
    ): CreateInvitationUseCase {
        return CreateInvitationUseCase(
            invitationRemoteRepository = invitationRemoteRepository,
            invitationRepository = invitationRepository
        )
    }

    @Provides
    fun provideDeleteInvitationUseCase(
        invitationRemoteRepository: InvitationRemoteRepository,
        invitationRepository: InvitationRepository
    ): DeleteInvitationUseCase {
        return DeleteInvitationUseCase(
            invitationRepository = invitationRepository,
            invitationRemoteRepository = invitationRemoteRepository
        )
    }

}