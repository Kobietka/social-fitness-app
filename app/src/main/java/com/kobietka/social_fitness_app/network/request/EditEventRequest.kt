package com.kobietka.social_fitness_app.network.request



data class EditEventRequest(
    val id: String,
    val name: String,
    val description: String,
    val pointGoal: Int,
    val pointPerRep: Int,
    val pointPerMinute: Int
)
