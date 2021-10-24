package com.kobietka.social_fitness_app.domain.repository.remote

import com.kobietka.social_fitness_app.network.request.UpdateUserDataRequest
import com.kobietka.social_fitness_app.network.request.UpdateUserPasswordRequest
import com.kobietka.social_fitness_app.network.response.UpdateUserDataResponse
import com.kobietka.social_fitness_app.util.NetworkResult


interface UpdateUserRemoteRepository {
    suspend fun updateUserPassword(
        userId: String,
        updateUserPasswordRequest: UpdateUserPasswordRequest
    ): NetworkResult<Boolean>
    suspend fun updateUserData(
        userId: String,
        updateUserDataRequest: UpdateUserDataRequest
    ): NetworkResult<UpdateUserDataResponse>
}