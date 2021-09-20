package com.kobietka.social_fitness_app.domain.state


data class StandardTextFieldState(
    val text: String = "",
    val error: String = "",
    val label: String
)
