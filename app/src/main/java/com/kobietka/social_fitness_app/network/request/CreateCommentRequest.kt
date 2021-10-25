package com.kobietka.social_fitness_app.network.request



data class CreateCommentRequest(
    val postId: String,
    val content: String
)
