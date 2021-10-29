package com.kobietka.social_fitness_app.network.request



data class CreateActivityRequest(
    val eventId: String,
    val name: String,
    val value: Int
)
