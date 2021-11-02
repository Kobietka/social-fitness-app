package com.kobietka.social_fitness_app.presentation.group

import com.kobietka.social_fitness_app.domain.model.*


data class GroupScreenState(
    val user: User = User(id = "", nickname = "", email = ""),
    val isOwner: Boolean = false,
    val group: Group = Group(id = "", name = "", description = "", ownerId = ""),
    val posts: List<Post> = emptyList(),
    val events: List<Event> = emptyList(),
    val members: List<GroupMember> = emptyList()
)
