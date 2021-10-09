package com.kobietka.social_fitness_app.presentation.main_screen

import com.kobietka.social_fitness_app.domain.model.User

data class MainScreenState(
    val user: User = User(id = "", nickname = "", email = "")
)
