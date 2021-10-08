package com.kobietka.social_fitness_app.domain.service

import com.kobietka.social_fitness_app.network.request.UpdateUserPasswordRequest
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path


interface UpdateUserService {

    @PUT("/api/user/{userId}")
    suspend fun updateUserData(
        @Path("userId") userId: String,
        @Body updateUserPasswordRequest: UpdateUserPasswordRequest
    )

}