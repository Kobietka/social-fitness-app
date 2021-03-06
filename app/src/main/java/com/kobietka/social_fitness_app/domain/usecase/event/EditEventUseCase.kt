package com.kobietka.social_fitness_app.domain.usecase.event

import com.kobietka.social_fitness_app.data.entity.EventEntity
import com.kobietka.social_fitness_app.domain.repository.local.EventRepository
import com.kobietka.social_fitness_app.domain.repository.remote.EventRemoteRepository
import com.kobietka.social_fitness_app.network.request.EditEventRequest
import com.kobietka.social_fitness_app.util.DateUtil
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class EditEventUseCase(
    private val eventRemoteRepository: EventRemoteRepository,
    private val eventRepository: EventRepository,
    private val dateUtil: DateUtil
) {
    operator fun invoke(
        groupId: String,
        eventId: String,
        name: String,
        description: String,
        pointGoal: Int,
        pointPerRep: Int,
        pointPerMinute: Int
    ): Flow<Progress> = flow {
        emit(Progress.Loading)
        val result = eventRemoteRepository.editEvent(
            eventId = eventId,
            editEventRequest = EditEventRequest(
                id = eventId,
                name = name,
                description = description,
                pointGoal = pointGoal,
                pointPerRep = pointPerRep,
                pointPerMinute = pointPerMinute
            )
        )
        when(result){
            is NetworkResult.Success -> {
                result.data.let { eventDto ->
                    val startDate = dateUtil.localDateFrom(eventDto.startDate)
                    val endDate = dateUtil.localDateFrom(eventDto.endDate)

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
                            eventType = eventDto.eventType,
                            isActive = dateUtil.isNowBetweenDates(startDate, endDate)
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





















