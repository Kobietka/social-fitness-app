package com.kobietka.social_fitness_app.domain.service

import com.kobietka.social_fitness_app.network.request.CreateActivityRequest
import com.kobietka.social_fitness_app.network.response.ActivityDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path


interface ActivityService {

    @POST("/api/activity")
    suspend fun createActivity(@Body createActivityRequest: CreateActivityRequest): ActivityDto

    @DELETE("/api/activity/{id}")
    suspend fun deleteActivity(@Path("id") activityId: String)

}