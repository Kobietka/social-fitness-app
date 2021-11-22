package com.kobietka.social_fitness_app.presentation.edit_event

import com.kobietka.social_fitness_app.domain.model.Event
import com.kobietka.social_fitness_app.domain.model.Group


data class EditEventScreenState(
    val event: Event? = null,
    val group: Group? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)