package com.kobietka.social_fitness_app.domain.usecase.event

import com.kobietka.social_fitness_app.data.entity.EventEntity
import com.kobietka.social_fitness_app.domain.repository.local.EventRepository
import com.kobietka.social_fitness_app.domain.repository.remote.EventRemoteRepository
import com.kobietka.social_fitness_app.network.request.CreateEventRequest
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class CreateEventUseCase(
    private val eventRemoteRepository: EventRemoteRepository,
    private val eventRepository: EventRepository
) {
    operator fun invoke(
        groupId: String,
        name: String,
        description: String,
        pointGoal: Int,
        pointPerRep: Int,
        pointPerMinute: Int,
        startDate: String,
        endDate: String,
        eventType: String
    ): Flow<Progress> = flow {
        emit(Progress.Loading)
        val result = eventRemoteRepository.createEvent(
            CreateEventRequest(
                groupId = groupId,
                name = name,
                description = description,
                pointGoal = pointGoal,
                pointPerRep = pointPerRep,
                pointPerMinute = pointPerMinute,
                startDate = startDate,
                endDate = endDate,
                eventType = eventType
            )
        )
        when(result){
            is NetworkResult.Success -> {
                result.data.let { eventDto ->
                    eventRepository.insert(
                        EventEntity(
                            id = eventDto.id,
                            name = eventDto.name,
                            description = eventDto.description,
                            groupId = groupId,
                            pointGoal = eventDto.pointGoal,
                            pointPerRep = eventDto.pointPerRep,
                            pointPerMinute = eventDto.pointPerMinute,
                            startDate = eventDto.startDate,
                            endDate = eventDto.endDate,
                            eventType = eventDto.eventType
                        )
                    )
                    emit(Progress.Finished)
                }
            }
            is NetworkResult.Failure -> emit(Progress.Error(message = result.message))
            is NetworkResult.Unauthorized -> emit(Progress.Unauthorized)
        }
    }
}
























