package com.kobietka.social_fitness_app.presentation.edit_user

import com.kobietka.social_fitness_app.domain.model.User


data class EditUserScreenState(
    val isDataLoading: Boolean = false,
    val isPasswordLoading: Boolean = false,
    val dataError: String = "",
    val passwordError: String = "",
    val user: User = User(id = "", nickname = "")
)
