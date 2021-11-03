package com.kobietka.social_fitness_app.presentation.create_post

import com.kobietka.social_fitness_app.domain.model.Group


data class CreatePostScreenState(
    val group: Group = Group(id = "", name = "", description = "", ownerId = ""),
    val isCreatingPost: Boolean = false,
    val creatingPostError: String = ""
)
