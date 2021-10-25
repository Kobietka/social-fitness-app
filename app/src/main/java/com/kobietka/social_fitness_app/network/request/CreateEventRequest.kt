package com.kobietka.social_fitness_app.network.request



data class CreateEventRequest(
    val groupId: String,
    val name: String,
    val description: String,
    val pointGoal: Int,
    val pointPerRep: Int,
    val pointPerMinute: Int,
    val startDate: String,
    val endDate: String,
    val eventType: String
)