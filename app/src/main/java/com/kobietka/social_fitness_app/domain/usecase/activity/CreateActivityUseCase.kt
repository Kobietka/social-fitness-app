package com.kobietka.social_fitness_app.domain.usecase.activity

import com.kobietka.social_fitness_app.data.entity.ActivityEntity
import com.kobietka.social_fitness_app.domain.repository.local.ActivityRepository
import com.kobietka.social_fitness_app.domain.repository.local.UserCredentialsRepository
import com.kobietka.social_fitness_app.domain.repository.remote.ActivityRemoteRepository
import com.kobietka.social_fitness_app.network.request.CreateActivityRequest
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class CreateActivityUseCase(
    private val activityRemoteRepository: ActivityRemoteRepository,
    private val activityRepository: ActivityRepository,
    private val userCredentialsRepository: UserCredentialsRepository
) {
    operator fun invoke(
        eventId: String,
        name: String,
        value: Int
    ): Flow<Progress> = flow {
        emit(Progress.Loading)
        val userId = userCredentialsRepository.getUserId()
        val result = activityRemoteRepository.createActivity(
            createActivityRequest = CreateActivityRequest(
                eventId = eventId,
                name = name,
                value = value
            )
        )
        when(result){
            is NetworkResult.Success -> {
                result.data.let { activityDto ->
                    activityRepository.insert(
                        ActivityEntity(
                            id = activityDto.id,
                            name = activityDto.name,
                            eventId = eventId,
                            userId = userId,
                            value = activityDto.value,
                            createdAt = activityDto.createdAt
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























