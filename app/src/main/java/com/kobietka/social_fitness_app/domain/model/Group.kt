package com.kobietka.social_fitness_app.domain.model



data class Group(
    val id: String,
    val name: String,
    val description: String,
    val ownerId: String,
    val invitationCode: String?
)
