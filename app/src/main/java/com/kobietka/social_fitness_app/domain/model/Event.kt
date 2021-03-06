package com.kobietka.social_fitness_app.domain.model



data class Event(
    val id: String,
    val groupId: String,
    val isActive: Boolean,
    val name: String,
    val description: String,
    val pointGoal: Int,
    val pointPerMinute: Int,
    val pointPerRep: Int,
    val startDate: String,
    val endDate: String,
    val eventType: EventType
)
