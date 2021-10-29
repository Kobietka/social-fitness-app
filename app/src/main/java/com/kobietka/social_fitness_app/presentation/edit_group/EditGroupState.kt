package com.kobietka.social_fitness_app.presentation.edit_group

import com.kobietka.social_fitness_app.domain.model.Group


data class EditGroupState(
    val isUpdatingGroup: Boolean = false,
    val updateMessage: String = "",
    val updatingGroupError: String = "",
    val group: Group = Group(id = "", name = "", description = "", ownerId = "")
)
