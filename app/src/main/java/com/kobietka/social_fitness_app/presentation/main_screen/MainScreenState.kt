package com.kobietka.social_fitness_app.presentation.main_screen

import com.kobietka.social_fitness_app.domain.model.Group
import com.kobietka.social_fitness_app.domain.model.User

data class MainScreenState(
    val user: User = User(id = "", nickname = "", email = ""),
    val groups: List<Group> = emptyList(),
    val isCreatingGroup: Boolean = false,
    val isJoiningGroup: Boolean = false,
    val joinGroupError: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val isUpdatingGroups: Boolean = false,
    val updatingGroupsMessage: String = ""
)
