package com.kobietka.social_fitness_app.domain.usecase.auth

import com.kobietka.social_fitness_app.domain.model.RegisterValidationResult
import com.kobietka.social_fitness_app.presentation.register.isValidEmail


class ValidateRegisterUseCase {
    operator fun invoke(
        nickname: String,
        email: String,
        password: String,
        repeatPassword: String
    ): RegisterValidationResult {
        if(nickname.length < 4) return RegisterValidationResult.NicknameTooShort
        if(!isValidEmail(email = email)) return RegisterValidationResult.EmailNotValid
        if(password.length < 8) return RegisterValidationResult.PasswordTooShort
        if(password != repeatPassword) return RegisterValidationResult.PasswordsAreNotTheSame

        return RegisterValidationResult.Success
    }
}