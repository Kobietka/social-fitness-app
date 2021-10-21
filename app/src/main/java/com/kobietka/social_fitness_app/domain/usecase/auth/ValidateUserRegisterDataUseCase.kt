package com.kobietka.social_fitness_app.domain.usecase.auth

import com.kobietka.social_fitness_app.domain.model.RegisterDataValidationResult
import com.kobietka.social_fitness_app.presentation.register.isValidEmail


class ValidateUserRegisterDataUseCase {
    operator fun invoke(
        nickname: String,
        email: String,
        password: String,
        repeatPassword: String
    ): RegisterDataValidationResult {
        if(nickname.length < 4) return RegisterDataValidationResult.NicknameTooShort
        if(!isValidEmail(email = email)) return RegisterDataValidationResult.EmailNotValid
        if(password.length < 8) return RegisterDataValidationResult.PasswordTooShort
        if(password != repeatPassword) return RegisterDataValidationResult.PasswordsAreNotTheSame

        return RegisterDataValidationResult.Success
    }
}