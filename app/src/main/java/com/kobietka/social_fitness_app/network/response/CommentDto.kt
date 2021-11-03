package com.kobietka.social_fitness_app.network.response

import com.kobietka.social_fitness_app.domain.model.User


data class CommentDto(
    val id: String,
    val content: String,
    val createdBy: User,
    val createdAt: String
)
