package com.kobietka.social_fitness_app.domain.model



sealed class RegisterValidationResult {
    object Success : RegisterValidationResult()
    object EmailNotValid : RegisterValidationResult()
    object PasswordsAreNotTheSame : RegisterValidationResult()
    object NicknameTooShort : RegisterValidationResult()
    object PasswordTooShort : RegisterValidationResult()
}
