package com.kobietka.social_fitness_app.network.response

import com.kobietka.social_fitness_app.domain.model.User


data class EventMemberDto(
    val user: User,
    val activities: List<ActivityDto>?,
    val totalScore: Int
)
