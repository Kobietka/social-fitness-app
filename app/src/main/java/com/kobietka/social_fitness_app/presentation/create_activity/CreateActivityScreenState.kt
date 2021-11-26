package com.kobietka.social_fitness_app.presentation.create_activity

import com.kobietka.social_fitness_app.domain.model.Event


data class CreateActivityScreenState(
    val event: Event? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)