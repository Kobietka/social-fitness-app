package com.kobietka.social_fitness_app.domain.usecase.activity

import com.kobietka.social_fitness_app.domain.repository.local.ActivityRepository
import com.kobietka.social_fitness_app.domain.repository.remote.ActivityRemoteRepository
import com.kobietka.social_fitness_app.util.NetworkResult
import com.kobietka.social_fitness_app.util.Progress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class DeleteActivityUseCase(
    private val activityRemoteRepository: ActivityRemoteRepository,
    private val activityRepository: ActivityRepository
) {
    operator fun invoke(activityId: String): Flow<Progress> = flow {
        emit(Progress.Loading)
        when(val result = activityRemoteRepository.deleteActivity(activityId = activityId)){
            is NetworkResult.Success -> {
                activityRepository.deleteActivity(id = activityId)
                emit(Progress.Finished)
            }
            is NetworkResult.Failure -> emit(Progress.Error(message = result.message))
            is NetworkResult.Unauthorized -> emit(Progress.Unauthorized)
        }
    }
}