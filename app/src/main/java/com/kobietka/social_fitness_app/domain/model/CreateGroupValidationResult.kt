package com.kobietka.social_fitness_app.domain.model


sealed class CreateGroupValidationResult {
    object Success : CreateGroupValidationResult()
    object GroupNameBlank : CreateGroupValidationResult()
    object GroupDescriptionBlank : CreateGroupValidationResult()
}
