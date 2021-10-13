package com.kobietka.social_fitness_app.network.response

import com.kobietka.social_fitness_app.domain.model.User


data class MemberDto(
    val id: String,
    val user: User,
    val assignedAt: String
)
