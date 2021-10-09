package com.kobietka.social_fitness_app.domain.model



sealed class UpdateUserDataValidationResult {
    object Success : UpdateUserDataValidationResult()
    object PasswordBlank : UpdateUserDataValidationResult()
    object NicknameTooShort : UpdateUserDataValidationResult()
    object NotAValidEmail : UpdateUserDataValidationResult()
}
