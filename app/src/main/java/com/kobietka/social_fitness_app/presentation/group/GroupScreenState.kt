package com.kobietka.social_fitness_app.presentation.group

import com.kobietka.social_fitness_app.domain.model.*


data class GroupScreenState(
    val user: User = User(id = "", nickname = "", email = ""),
    val isUpdating: Boolean = false,
    val updateError: String = "",
    val page: GroupPage = GroupPage.POSTS,
    val group: Group = Group(id = "", name = "", description = "", ownerId = ""),
    val posts: List<Post> = emptyList(),
    val activeEvents: List<Event> = emptyList(),
    val archivedEvents: List<Event> = emptyList(),
    val members: List<GroupMember> = emptyList()
)