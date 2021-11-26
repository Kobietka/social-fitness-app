package com.kobietka.social_fitness_app.domain.usecase.event

import com.kobietka.social_fitness_app.data.entity.ActivityEntity
import com.kobietka.social_fitness_app.data.entity.EventEntity
import com.kobietka.social_fitness_app.data.entity.EventMemberEntity
import com.kobietka.social_fitness_app.domain.repository.local.ActivityRepository
import com.kobietka.social_fitness_app.domain.repository.local.EventMemberRepository
import com.kobietka.social_fitness_app.domain.repository.local.EventRepository
import com.kobietka.social_fitness_app.domain.repository.remote.EventRemoteRepository
import com.kobietka.social_fitness_app.util.DateUtil
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.*


class GetRemoteEventUseCase(
    private val eventRemoteRepository: EventRemoteRepository,
    private val eventRepository: EventRepository,
    private val activityRepository: ActivityRepository,
    private val eventMemberRepository: EventMemberRepository,
    private val dateUtil: DateUtil
) {
    operator fun invoke(
        eventId: String,
        groupId: String
    ): Flow<Progress> = flow {
        emit(Progress.Loading)
        when(val result = eventRemoteRepository.getEvent(eventId = eventId)){
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
                    eventDto.eventMembers?.let { eventMembers ->
                        eventMembers.forEach { eventMemberDto ->
                            eventMemberRepository.insert(
                                EventMemberEntity(
                                    userId = eventMemberDto.user.id,
                                    eventId = eventId,
                                    nickname = eventMemberDto.user.nickname,
                                    totalScore = eventMemberDto.totalScore
                                )
                            )
                            eventMemberDto.activities?.let { activities ->
                                activities.forEach { activityDto ->
                                    activityRepository.insert(
                                        ActivityEntity(
                                            id = activityDto.id,
                                            userId = eventMemberDto.user.id,
                                            eventId = eventDto.id,
                                            name = activityDto.name,
                                            value = activityDto.value,
                                            createdAt = activityDto.createdAt
                                        )
                                    )
                                }
                            }
                        }
                    }
                    emit(Progress.Finished)
                }
            }
            is NetworkResult.Failure -> emit(Progress.Error(message = result.message))
            is NetworkResult.Unauthorized -> emit(Progress.Unauthorized)
        }
    }
}


















