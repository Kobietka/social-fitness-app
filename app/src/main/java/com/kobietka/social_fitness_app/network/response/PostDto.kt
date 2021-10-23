package com.kobietka.social_fitness_app.network.response

import com.kobietka.social_fitness_app.domain.model.User


data class PostDto(
    val id: String,
    val content: String,
    val user: User,
    val comments: List<CommentDto>,
    val createdAt: String
)
