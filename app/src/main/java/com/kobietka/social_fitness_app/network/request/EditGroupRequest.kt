package com.kobietka.social_fitness_app.network.request



data class EditGroupRequest(
    val id: String,
    val name: String,
    val description: String
)
