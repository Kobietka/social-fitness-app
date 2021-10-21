package com.kobietka.social_fitness_app.domain.model



sealed class RegisterDataValidationResult {
    object Success : RegisterDataValidationResult()
    object EmailNotValid : RegisterDataValidationResult()
    object PasswordsAreNotTheSame : RegisterDataValidationResult()
    object NicknameTooShort : RegisterDataValidationResult()
    object PasswordTooShort : RegisterDataValidationResult()
}
