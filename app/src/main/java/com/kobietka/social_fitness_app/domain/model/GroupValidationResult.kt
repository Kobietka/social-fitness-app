package com.kobietka.social_fitness_app.domain.model


sealed class GroupValidationResult {
    object Success : GroupValidationResult()
    object GroupNameBlank : GroupValidationResult()
    object GroupDescriptionBlank : GroupValidationResult()
}
