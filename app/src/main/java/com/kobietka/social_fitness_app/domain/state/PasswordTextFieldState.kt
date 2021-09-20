package com.kobietka.social_fitness_app.domain.state


data class PasswordTextFieldState(
    val text: String = "",
    val error: String = "",
    val isVisible: Boolean = false,
    val label: String
)