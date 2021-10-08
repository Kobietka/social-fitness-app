package com.kobietka.social_fitness_app.domain.model

sealed class UpdatePasswordValidationResult {
    object Success : UpdatePasswordValidationResult()
    object PasswordBlank : UpdatePasswordValidationResult()
    object NewPasswordTooShort : UpdatePasswordValidationResult()
}