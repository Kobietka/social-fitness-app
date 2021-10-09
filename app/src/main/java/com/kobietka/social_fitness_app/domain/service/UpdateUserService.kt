package com.kobietka.social_fitness_app.domain.service

import com.kobietka.social_fitness_app.network.request.UpdateUserDataRequest
import com.kobietka.social_fitness_app.network.request.UpdateUserPasswordRequest
import com.kobietka.social_fitness_app.network.response.UpdateUserDataResponse
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Path


interface UpdateUserService {

    @PUT("/api/user/{userId}")
    suspend fun updateUserPassword(
        @Path("userId") userId: String,
        @Body updateUserPasswordRequest: UpdateUserPasswordRequest
    )

    @PATCH("/api/user/{userId}")
    suspend fun updateUserData(
        @Path("userId") userId: String,
        @Body updateUserDataRequest: UpdateUserDataRequest
    ): UpdateUserDataResponse

}