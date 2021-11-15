package com.kobietka.social_fitness_app.presentation.create_event

import com.kobietka.social_fitness_app.domain.model.Group


data class CreateEventScreenState(
    val isLoading: Boolean = false,
    val createEventError: String = "",
    val group: Group? = null
)
