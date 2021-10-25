package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.local.EventRepository
import com.kobietka.social_fitness_app.domain.repository.remote.EventRemoteRepository
import com.kobietka.social_fitness_app.domain.usecase.event.CreateEventUseCase
import com.kobietka.social_fitness_app.domain.usecase.event.EditEventUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class EventModule {

    @Provides
    fun provideCreateEventUseCase(
        eventRemoteRepository: EventRemoteRepository,
        eventRepository: EventRepository
    ): CreateEventUseCase {
        return CreateEventUseCase(
            eventRemoteRepository = eventRemoteRepository,
            eventRepository = eventRepository
        )
    }

    @Provides
    fun provideEditEventUseCase(
        eventRemoteRepository: EventRemoteRepository,
        eventRepository: EventRepository
    ): EditEventUseCase {
        return EditEventUseCase(
            eventRemoteRepository = eventRemoteRepository,
            eventRepository = eventRepository
        )
    }

}























