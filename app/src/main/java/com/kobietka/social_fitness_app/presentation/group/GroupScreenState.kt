package com.kobietka.social_fitness_app.presentation.group

import com.kobietka.social_fitness_app.domain.model.Event
import com.kobietka.social_fitness_app.domain.model.Group
import com.kobietka.social_fitness_app.domain.model.GroupMember
import com.kobietka.social_fitness_app.domain.model.Post


data class GroupScreenState(
    val group: Group = Group(id = "", name = "", description = "", ownerId = ""),
    val posts: List<Post> = emptyList(),
    val events: List<Event> = emptyList(),
    val members: List<GroupMember> = emptyList()
)
