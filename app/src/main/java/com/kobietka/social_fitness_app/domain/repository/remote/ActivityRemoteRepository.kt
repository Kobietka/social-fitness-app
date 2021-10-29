package com.kobietka.social_fitness_app.domain.repository.remote

import com.kobietka.social_fitness_app.network.request.CreateActivityRequest
import com.kobietka.social_fitness_app.network.response.ActivityDto
import com.kobietka.social_fitness_app.util.NetworkResult


interface ActivityRemoteRepository {
    suspend fun createActivity(createActivityRequest: CreateActivityRequest): NetworkResult<ActivityDto>
    suspend fun deleteActivity(activityId: String): NetworkResult<Boolean>
}