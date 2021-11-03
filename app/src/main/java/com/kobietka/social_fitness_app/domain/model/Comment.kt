package com.kobietka.social_fitness_app.domain.model



data class Comment(
    val id: String,
    val user: GroupMember,
    val content: String,
    val createdAt: String
)
