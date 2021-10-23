package com.kobietka.social_fitness_app.network.request



data class CreatePostRequest(
    val groupId: String,
    val content: String
)
