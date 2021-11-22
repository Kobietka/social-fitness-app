package com.kobietka.social_fitness_app.presentation.event

import com.kobietka.social_fitness_app.domain.model.Event
import com.kobietka.social_fitness_app.domain.model.EventMember
import com.kobietka.social_fitness_app.domain.model.Group
import com.kobietka.social_fitness_app.domain.model.User

data class EventScreenState(
    val loggedUser: User? = null,
    val event: Event? = null,
    val isUserAGroupOwner: Boolean = false,
    val eventMembers: List<EventMember> = emptyList(),
    val group: Group? = null
)