package com.kobietka.social_fitness_app.domain.model



data class Activity(
    val id: String,
    val userId: String,
    val name: String,
    val value: Int,
    val createdAt: String
)
