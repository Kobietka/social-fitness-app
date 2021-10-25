package com.kobietka.social_fitness_app.network.response



data class EventDto(
    val id: String,
    val isActive: Boolean,
    val name: String,
    val description: String,
    val pointGoal: Int,
    val pointPerRep: Int,
    val pointPerMinute: Int,
    val startDate: String,
    val endDate: String,
    val eventType: String,
    val eventMembers: List<EventMemberDto>?
)
