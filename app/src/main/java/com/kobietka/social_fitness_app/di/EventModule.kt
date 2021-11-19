package com.kobietka.social_fitness_app.di

import com.kobietka.social_fitness_app.domain.repository.local.ActivityRepository
import com.kobietka.social_fitness_app.domain.repository.local.EventMemberRepository
import com.kobietka.social_fitness_app.domain.repository.local.EventRepository
import com.kobietka.social_fitness_app.domain.repository.remote.EventRemoteRepository
import com.kobietka.social_fitness_app.domain.usecase.event.*
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
    fun provideGetEventsForGroupUseCase(
        eventRepository: EventRepository
    ): GetEventsForGroupUseCase {
        return GetEventsForGroupUseCase(eventRepository = eventRepository)
    }

    @Provides
    fun provideValidateCreateEventUseCase(): ValidateCreateEventUseCase {
        return ValidateCreateEventUseCase()
    }

    @Provides
    fun provideDeleteEventUseCase(
        eventRemoteRepository: EventRemoteRepository,
        eventRepository: EventRepository
    ): DeleteEventUseCase {
        return DeleteEventUseCase(
            eventRepository = eventRepository,
            eventRemoteRepository = eventRemoteRepository
        )
    }

    @Provides
    fun provideMatchEventMembersWithActivitiesUseCase(
        activityRepository: ActivityRepository
    ): MatchEventMembersWithActivitiesUseCase {
        return MatchEventMembersWithActivitiesUseCase(activityRepository = activityRepository)
    }

    @Provides
    fun provideGetEventMembersUseCase(
        eventMemberRepository: EventMemberRepository
    ): GetEventMembersUseCase {
        return GetEventMembersUseCase(eventMemberRepository = eventMemberRepository)
    }

    @Provides
    fun provideGetEventUseCase(
        eventRepository: EventRepository
    ): GetEventUseCase {
        return GetEventUseCase(eventRepository = eventRepository)
    }

    @Provides
    fun provideGetRemoteEventUseCase(
        eventRemoteRepository: EventRemoteRepository,
        eventRepository: EventRepository,
        activityRepository: ActivityRepository
    ): GetRemoteEventUseCase {
        return GetRemoteEventUseCase(
            eventRepository = eventRepository,
            eventRemoteRepository = eventRemoteRepository,
            activityRepository = activityRepository
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























