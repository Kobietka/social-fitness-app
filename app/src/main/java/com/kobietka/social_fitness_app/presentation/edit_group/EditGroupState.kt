package com.kobietka.social_fitness_app.presentation.edit_group

import com.kobietka.social_fitness_app.domain.model.Group
import com.kobietka.social_fitness_app.domain.model.Invitation


data class EditGroupState(
    val isUpdatingGroup: Boolean = false,
    val isCreatingCode: Boolean = false,
    val isDeletingCode: Boolean = false,
    val updateMessage: String = "",
    val updatingGroupError: String = "",
    val group: Group = Group(id = "", name = "", description = "", ownerId = ""),
    val invitation: Invitation? = null
)
