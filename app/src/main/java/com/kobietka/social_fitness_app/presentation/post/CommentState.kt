package com.kobietka.social_fitness_app.presentation.post

import com.kobietka.social_fitness_app.domain.model.Comment
import com.kobietka.social_fitness_app.domain.state.StandardTextFieldState


data class CommentState(
    val comment: Comment,
    val commentEditValue: StandardTextFieldState,
    val isExpanded: Boolean = false,
    val isLoading: Boolean = false,
    val isEditing: Boolean = false
)
