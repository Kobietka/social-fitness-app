package com.kobietka.social_fitness_app.domain.model



data class Post(
    val id: String,
    val content: String,
    val user: GroupMember,
    val createdAt: String
)
